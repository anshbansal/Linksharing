package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.ReadingItemCO
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User
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

    List<ReadingItemCO> recentPublicResources() {
        List<ReadingItemCO> recentResources = []

        Resource.recentPublicResources.list(max: 5).each { Resource resource ->
            recentResources << new ReadingItemCO(resource: resource)
        }

        return recentResources
    }
}
