package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

import static com.ttd.linksharing.util.ServiceUtil.validateAndSave

@Transactional
class ResourceService {

    def readingItemService

    Resource save(Resource resource, Map args = [:]) {

        if (!validateAndSave(resource, args)) {
            return null
        }

        List<Subscription> resourceSubscriptions = Subscription.findAllWhere(topic: resource.topic)

        resourceSubscriptions*.user.each { User subscribedUser ->
            ReadingItem readingItem = new ReadingItem(resource: resource, user: subscribedUser)
            readingItemService.save(readingItem, args)
        }
        resource
    }

    Boolean isRead(Resource resource, User user) {
        ReadingItem readingItem = ReadingItem.findWhere(resource: resource, user: user)
        readingItem ? readingItem.isRead : Boolean.FALSE
    }

    List<Resource> getRecentShares() {
        //TODO Depending on User and topic type change results
        return Resource.list(sort: 'dateCreated', order: 'desc', max: 5)
    }
}
