package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import grails.transaction.Transactional

@Transactional
class TopicService {

    def subscriptionService

    Topic save(Topic topic, Boolean isFlushEnabled = false, Boolean failOnError = false) {
        if(topic.validate() && topic.save(flush: isFlushEnabled, failOnError: failOnError)) {
            Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
            subscriptionService.save(subscription, isFlushEnabled, failOnError)

            //TODO Why was this needed?
            topic.addToSubscriptions(subscription)
            topic.save(flush: isFlushEnabled, failOnError: failOnError)
        } else {
            topic = null
        }
        topic
    }

}
