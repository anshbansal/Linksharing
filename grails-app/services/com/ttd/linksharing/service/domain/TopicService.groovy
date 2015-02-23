package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import grails.transaction.Transactional

import static com.ttd.linksharing.util.ServiceUtil.validateAndSave

@Transactional
class TopicService {

    def subscriptionService

    Topic save(Topic topic, Map args) {

        if (!validateAndSave(topic, args)) {
            return null
        }
        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, args)
        topic
    }

}
