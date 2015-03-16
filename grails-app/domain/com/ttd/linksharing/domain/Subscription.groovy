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
        forPublicTopics {
            'topic' {
                eq 'scope', Visibility.PUBLIC
            }
        }

        subscriptionForUser { User user ->
            eq 'user', user
        }

        subscriptionForTopic { Topic topic ->
            eq 'topic', topic
        }

        showSubscriptionToUser { User user ->
            or {
                forPublicTopics()
                subscriptionForUser(user)
            }
        }

        topicNameLike { String term ->
            'topic' {
                ilike 'name', '%' + term + '%'
            }
        }
    }
}
