package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class TopicService {

    def subscriptionService

    Topic create(TopicInfo info, User user) {
        if (isTopicPresentForUser(user, info.topicName)) {
            return null
        }
        save(new Topic(info, user))
    }

    def save(Topic topic, Boolean isFlushEnabled = false) {

        if (! topic.save(flush: isFlushEnabled)) {
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)

        topic
    }

    List<Topic> getSubscriptionsForUser(User user) {
        Subscription.subscribedTopics(user).list(max: 5).topic
    }

    Boolean isTopicPresentForUser(User user, String topicName) {
        Topic.createCriteria().count {
            eq 'createdBy', user
            eq 'name', topicName
        } > 0
    }
}
