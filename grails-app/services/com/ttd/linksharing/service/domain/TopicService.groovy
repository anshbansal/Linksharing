package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class TopicService {

    def subscriptionService
    def userService

    def save(Topic topic, Boolean isFlushEnabled = false) {

        if (! topic.save(flush: isFlushEnabled)) {
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)
    }

    List<Topic> getSubscriptionsForUser(User user) {
        Subscription.subscribedTopics(user).list(max: 5).topic
    }

    Boolean isTopicPresentForUser(String username, String topicName) {
        if (username == null) {
            return false
        }
        User user = userService.forUsername(username)

        Topic.findByCreatedBy(user).name == topicName
    }
}
