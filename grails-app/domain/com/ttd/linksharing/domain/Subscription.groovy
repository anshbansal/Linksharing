package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness
import org.hibernate.FetchMode

class Subscription {
    Seriousness seriousness = Seriousness.SERIOUS
    Date dateCreated

    static belongsTo = [user: User, topic: Topic]

    static constraints = {
        seriousness nullable: true
        topic unique: 'user'
    }

    static namedQueries = {
        forUser { User user ->
            createAlias 'topic', 't'
            eq 'user', user

            fetchMode('topic', FetchMode.JOIN)
            fetchMode('topic.createdBy', FetchMode.JOIN)
        }

        publicTopics {
            'topic' {
                Topic.publicTopics
            }
        }

        topicNameLike { String term ->
            'topic' {
                Topic.topicNameLike(term)
            }
        }

        forTopic { Topic topic ->
            eq 'topic', topic
            fetchMode('topic', FetchMode.JOIN)
        }

        orderByResourceAddDate {
            createAlias 'topic', 't'
            createAlias 't.resources', 'r'

            projections {
                groupProperty 'topic'
                max 'r.dateCreated', 'resourceDate'
            }
            order 'resourceDate', 'desc'
        }

        orderByTopicName {
            createAlias 'topic', 't'

            projections {
                property('topic')
            }

            order 't.name', 'asc'
        }
    }
}
