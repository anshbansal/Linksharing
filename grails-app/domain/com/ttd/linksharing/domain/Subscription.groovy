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
        subscribedTopics { User user ->
            eq 'user', user
            order("dateCreated", "desc")

            fetchMode('topic', FetchMode.JOIN)
            fetchMode('topic.createdBy', FetchMode.JOIN)
        }
    }
}
