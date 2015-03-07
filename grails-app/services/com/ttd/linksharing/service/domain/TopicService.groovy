package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class TopicService {

    def subscriptionService
    def userService

    def create(TopicInfo info, User user) {
        //TODO Implement
        save(info.topic)
    }

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

    Boolean isTopicPresentForUser(User user, String topicName) {
//        if (username == null) {
//            return false
//        }
//        User user = userService.forUsername(username)

        //TODO Change to criteria for user and topic name
        Topic.findByCreatedBy(user).name == topicName
    }
}
