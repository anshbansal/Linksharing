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
        getPostDetailsFromBaseCriteria(Resource.recentResources, params)
    }

    PagedResult<PostDetails> getPostsForUserId(Long userId, QueryParameters params) {
        getPostDetailsFromBaseCriteria(Resource.resourceForUserId(userId), params)
    }

    PagedResult<PostDetails> getPostsForTopic(Topic topic, QueryParameters params) {
        println "Params are ${params}"
        getPostDetailsFromBaseCriteria(Resource.resourceForTopic(topic), params)
    }

    private static PagedResult<PostDetails> getPostDetailsFromBaseCriteria(def criteria, QueryParameters params) {
        criteria = getFilteredCriteria(criteria, params)
        getPostDetailsFromCriteria(criteria, params, PostDetails.mapFromResource)
    }

    private static PagedResult<PostDetails> getPostDetailsFromCriteria(def criteria, QueryParameters params, Closure collector) {
        List<PagedResultList> pagedResultList = criteria.list(params.queryMapParams)

        new PagedResult<PostDetails>().setPaginationList(pagedResultList, collector)
    }

    static def getFilteredCriteria(def criteria, QueryParameters params) {

        if (params.loggedUser) {
            if (! params.loggedUser.admin) {
                println "Paramas are ${params}"
                criteria = criteria.showResourceToUser(params.loggedUser)
            }
        } else {
            criteria = criteria.publicResources()
        }


        if (params.searchTerm) {
            criteria = criteria.descriptionLike(params.searchTerm)
        }
        return criteria
    }
}
