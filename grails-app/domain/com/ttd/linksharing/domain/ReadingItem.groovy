package com.ttd.linksharing.domain

import org.hibernate.FetchMode

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]

    static namedQueries = {

        unreadForUser { User user ->
            projections {
                property('resource')
            }

            eq('isRead', Boolean.FALSE)
            eq('user', user)
            fetchMode('resource', FetchMode.JOIN)
        }
    }
}
