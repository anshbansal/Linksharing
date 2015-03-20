package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.*
import com.ttd.linksharing.enums.Visibility
import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.vo.QueryParameters
import grails.gorm.PagedResultList
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def topicService
    def readingItemService
    def subscriptionService

    Resource save(Resource resource, Boolean isFlushEnabled = false) {

        if (!resource.save(flush: isFlushEnabled)) {
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

        PagedResultList recentResources = Resource.createCriteria().list(params.queryMapParams) {
            createAlias('topic', 't')

            filterResourcesToBeShownToUserForSearchTerm.delegate = delegate
            filterResourcesToBeShownToUserForSearchTerm(params, 't')

            order("dateCreated", "desc")
        }

        getPostDetailsFromPagedResultList(recentResources)
    }

    PagedResult<PostDetails> getPostsForUserId(Long userId, QueryParameters params) {

        PagedResultList resourcesForCreatorId = Resource.createCriteria().list(params.queryMapParams) {
            createAlias('topic', 't')

            'createdBy' {
                eq 'id', userId
            }

            filterResourcesToBeShownToUserForSearchTerm.delegate = delegate
            filterResourcesToBeShownToUserForSearchTerm(params, 't')
        }

        getPostDetailsFromPagedResultList(resourcesForCreatorId)
    }

    PagedResult<PostDetails> getPostsForTopic(Topic topic, QueryParameters params) {

        PagedResultList resourcesForTopic = Resource.createCriteria().list(params.queryMapParams) {
            createAlias('topic', 't')

            eq 'topic', topic

            filterResourcesToBeShownToUserForSearchTerm.delegate = delegate
            filterResourcesToBeShownToUserForSearchTerm(params, 't')
        }

        getPostDetailsFromPagedResultList(resourcesForTopic)
    }

    PagedResult<PostDetails> getPostsForAllTopics(QueryParameters params) {
        PagedResultList resourcesForTopic = Resource.createCriteria().list(params.queryMapParams) {

            createAlias('topic', 't')
            
            TopicService.filterTopicToBeShownToUserForSearchTerm.delegate = delegate
            TopicService.filterTopicToBeShownToUserForSearchTerm(params.loggedUser, 't', null)

            if (params.searchTerm) {
                or {
                    ilike 't.name', '%' + params.searchTerm + '%'
                    ilike 'description', '%' + params.searchTerm + '%'
                }
            }
            order("dateCreated", "desc")
        }
        getPostDetailsFromPagedResultList(resourcesForTopic)
    }

    private PagedResult<PostDetails> getPostDetailsFromPagedResultList(PagedResultList pagedResultList) {
        PagedResult<PostDetails> result = new PagedResult<>()
        result.paginationList = PostDetails.mapFromResource(pagedResultList)
        result.totalCount = pagedResultList.totalCount

        result
    }

    def filterResourcesToBeShownToUserForSearchTerm = { QueryParameters params, String topicAlias ->
        TopicService.filterTopicToBeShownToUserForSearchTerm.delegate = delegate
        TopicService.filterTopicToBeShownToUserForSearchTerm(params.loggedUser, topicAlias, null)

        if (params.searchTerm) {
            ilike 'description', '%' + params.searchTerm + '%'
        }
    }
}
