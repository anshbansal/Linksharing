package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness
import com.ttd.linksharing.enums.Visibility
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

        publicTopics {
            'topic' {
                eq 'scope', Visibility.PUBLIC
            }
        }

        topicNameLike { String term ->
            'topic' {
                ilike 'name', '%' + term + '%'
            }
        }

        forTopic { Topic topic ->
            eq 'topic', topic
            fetchMode('topic', FetchMode.JOIN)
        }
    }
}
