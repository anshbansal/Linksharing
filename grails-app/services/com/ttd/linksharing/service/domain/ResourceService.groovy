package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.util.Mappings
import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.vo.QueryParameters
import grails.gorm.PagedResultList
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def readingItemService

    Resource save(Resource resource, Boolean isFlushEnabled = false) {

        if (! resource.save(flush: isFlushEnabled)) {
            return null
        }

        List<Subscription> resourceSubscriptions = Subscription.findAllWhere(topic: resource.topic)

        resourceSubscriptions*.user.each { User subscribedUser ->
            ReadingItem readingItem = new ReadingItem(resource: resource, user: subscribedUser)
            readingItemService.save(readingItem, isFlushEnabled)
        }
        resource
    }

    PagedResult<PostDetails> recentPublicResources(QueryParameters params) {

        def coreBusinessLogicCriteriaForRecentResources = Resource.recentResources
        List<PagedResultList> recentResources = getResourcesPagedResultListForLoggedUserAndSearch(coreBusinessLogicCriteriaForRecentResources, params)


        getPostDetailsFromCriteria(recentResources)
    }

    PagedResult<PostDetails> getPostsForUserId(Long userId, QueryParameters params) {

        def coreBusinessLogicCriteria = Resource.resourcesForCreatorId(userId)
        List<PagedResultList> resourcesForCreatorId = getResourcesPagedResultListForLoggedUserAndSearch(coreBusinessLogicCriteria, params)

        getPostDetailsFromCriteria(resourcesForCreatorId)
    }

    PagedResult<PostDetails> getPostsForTopic(Topic topic, QueryParameters params) {

        def coreBusinessLogicCriteria = Resource.resourcesOfTopic(topic)
        List<PagedResultList> resourcesForTopic = getResourcesPagedResultListForLoggedUserAndSearch(coreBusinessLogicCriteria, params)

        getPostDetailsFromCriteria(resourcesForTopic)
    }

    private static PagedResult<PostDetails> getPostDetailsFromCriteria(List<PagedResultList> pagedResultList) {
        new PagedResult<PostDetails>().setPaginationList(pagedResultList, PostDetails.mapFromResource)
    }

    static List<PagedResultList> getResourcesPagedResultListForLoggedUserAndSearch(def criteria, QueryParameters params) {

        if (params.loggedUser) {
            if (! params.loggedUser.admin) {
                List<Long> subscribedPrivateTopicIdsForUser = SubscriptionService.getSubscribedPrivateTopicIdsForUser(params.loggedUser)

                criteria = criteria.resourcesOfPublicTopicsOrHavingTopicIds(subscribedPrivateTopicIdsForUser)
            }
        } else {
            criteria = criteria.resourcesOfPublicTopics()
        }

        if (params.searchTerm) {
            criteria = criteria.resourcesHavingDescriptionIlike(params.searchTerm)
        }
        criteria.list(params.queryMapParams)
    }
}
