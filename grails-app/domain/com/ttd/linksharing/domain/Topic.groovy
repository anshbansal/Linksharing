package com.ttd.linksharing.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.enums.Visibility
import org.hibernate.FetchMode

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

    static namedQueries = {
        nameLike {String term ->
            ilike 'name', '%' + term + '%'
        }

        forUser { User user ->
            eq 'createdBy', user
            fetchMode('createdBy', FetchMode.JOIN)
        }

        publicTopics {
            eq 'scope', Visibility.PUBLIC
        }

        showTopicToUser { User user ->
            or {
                publicTopics()
            }
        }

        isSubscribedToTopic { User user ->
            'subscriptions' {
                eq 'user', user
                eq 'topic', this
            }
        }
    }
}
