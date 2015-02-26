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
        subscribedTopicsForUser { String username ->
            projections {
                property('topic')
            }

            'user' {
                eq('username', username)
            }
            order("dateCreated", "desc")

            fetchMode('user', FetchMode.JOIN)
        }
    }
}
