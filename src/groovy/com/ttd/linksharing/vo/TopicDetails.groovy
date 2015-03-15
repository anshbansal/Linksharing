package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

class TopicDetails {
    Topic topic
    User creator
    Long numSubscriptions
    Long numResources

    Long getTopicId() {
        topic.id
    }

    static Closure mapFromSubscriptions = { List<Subscription> subscriptions ->
        subscriptions.collect([]) { Subscription subscription ->
            new TopicDetails(topic: subscription.topic, creator: subscription.topic.createdBy)

        }
    }

    static Closure mapFromTopics = { List<Topic> topics ->
        topics.collect([]) { Topic topic ->
            new TopicDetails(topic: topic, creator: topic.createdBy)
        }
    }

    @Override
    public String toString() {
        "TopicDetails{" +
                "topic=" + topic +
                ", creator=" + creator +
                ", numSubscriptions=" + numSubscriptions +
                ", numResources=" + numResources +
                '}';
    }
}
