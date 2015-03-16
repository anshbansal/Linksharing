package com.ttd.linksharing.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.enums.Visibility
import org.hibernate.FetchMode
import org.hibernate.criterion.DetachedCriteria

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

        topicForUser { User user ->
            eq 'createdBy', user
        }

        publicTopics {
            eq 'scope', Visibility.PUBLIC
        }

        showTopicToUser { User user ->
            or {
                eq 'scope', Visibility.PUBLIC
                subscribedTopics(user)
            }
        }

        subscribedTopics { User user ->
            createAlias('subscriptions', 's')
            createAlias('s.topic', 'subTopic')
            createAlias('s.user', 'subUser')

            and {
                eq 's.user', user
                idEq 'subTopic.id'
            }
        }
    }
}
