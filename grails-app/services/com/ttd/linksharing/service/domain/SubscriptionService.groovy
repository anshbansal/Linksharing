package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import grails.transaction.Transactional


@Transactional
class SubscriptionService {

    def readingItemService

    Subscription save(Subscription subscription, Boolean isFlushEnabled = false) {

        if (! subscription.save(flush: isFlushEnabled)) {
            return null
        }

        Resource.findAllWhere(topic: subscription.topic).each { Resource resource ->
            ReadingItem readingItem = new ReadingItem(resource: resource, user: subscription.user)
            readingItemService.save(readingItem, isFlushEnabled)
        }
        subscription
    }
}
