package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Topic
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

    def save(Resource resource, Boolean isFlushEnabled = false) {

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

    PagedResult<PostDetails> getPostsForUser(User user, QueryParameters params) {
        getPostDetailsFromBaseCriteria(Resource.forUser(user), params)
    }

    PagedResult<PostDetails> getPostsForTopic(Topic topic, QueryParameters params) {
        getPostDetailsFromBaseCriteria(Resource.forTopic(topic), params)
    }

    private static PagedResult<PostDetails> getPostDetailsFromBaseCriteria(def baseCriteria, QueryParameters params) {
        def criteria = baseCriteria
        if (! params.includePrivates) {
            criteria = criteria.publicResources()
        }
        if (params.searchTerm) {
            criteria = criteria.descriptionLike(params.searchTerm)
        }

        getPostDetailsFromCriteria(criteria, params, PostDetails.mapFromResource)
    }

    private static PagedResult<PostDetails> getPostDetailsFromCriteria(def criteria, QueryParameters params, Closure collector) {
        List<PagedResultList> pagedResultList = criteria.list(max: params.max, offset: params.offset)

        new PagedResult<PostDetails>().setPaginationList(pagedResultList, collector)
    }
}
