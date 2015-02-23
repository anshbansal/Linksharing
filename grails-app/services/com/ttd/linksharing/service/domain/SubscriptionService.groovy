package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import grails.transaction.Transactional

@Transactional
class SubscriptionService {

    def readingItemService

    Subscription save(Subscription subscription, Boolean isFlushEnabled = false, Boolean failOnError = false) {
        if(subscription.validate() && subscription.save(flush: isFlushEnabled, failOnError: failOnError)) {
            subscription.resources.each { Resource resource ->
                ReadingItem readingItem = new ReadingItem(resource: resource, user: subscription.user)
                readingItemService.save(readingItem, isFlushEnabled, failOnError)
            }
        } else {
            subscription = null
        }
        subscription
    }
}
