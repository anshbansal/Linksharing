package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import grails.transaction.Transactional

import static com.ttd.linksharing.util.ServiceUtil.validateAndSave

@Transactional
class SubscriptionService {

    def readingItemService

    Subscription save(Subscription subscription, Map args) {

        if (!validateAndSave(subscription, args)) {
            return null
        }

        Resource.findAllWhere(topic: subscription.topic).each { Resource resource ->
            ReadingItem readingItem = new ReadingItem(resource: resource, user: subscription.user)
            readingItemService.save(readingItem, args)
        }
        subscription
    }
}
