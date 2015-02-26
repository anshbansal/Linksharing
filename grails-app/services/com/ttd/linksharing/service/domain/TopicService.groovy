package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import grails.transaction.Transactional

@Transactional
class TopicService {

    def subscriptionService

    def save(Topic topic, Boolean isFlushEnabled = false) {

        if (! topic.save(flush: isFlushEnabled)) {
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)
    }

}
