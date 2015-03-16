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
        //Makes sense to fetch Topic because topic is subscribed for user
        forUser { User user ->
            eq 'user', user

            fetchMode('topic', FetchMode.JOIN)
            fetchMode('topic.createdBy', FetchMode.JOIN)
        }

        forPublicTopics {
            'topic' {
                publicTopics()
            }
            fetchMode('topic', FetchMode.JOIN)
        }

        topicNameLike { String term ->
            'topic' {
                nameLike(term)
            }
            fetchMode('topic', FetchMode.JOIN)
        }

        forTopic { Topic topic ->
            eq 'topic', topic
            fetchMode('topic', FetchMode.JOIN)
        }

        showSubscriptionToUser { User user ->
            or {
                forPublicTopics()
                forUser(user)
            }
        }
    }
}
