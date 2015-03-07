package com.ttd.linksharing.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.enums.Visibility

class Topic {
    String name
    Date dateCreated
    Date lastUpdated
    Visibility scope = Visibility.PUBLIC

    Topic(TopicInfo info, User createdBy) {
        name = info.topicName
        scope = info.topicVisibility
        this.createdBy = createdBy
    }

    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription]

    static constraints = {
        name unique: 'createdBy'
    }

    @Override
    String toString() {
        "$name"
    }
}
