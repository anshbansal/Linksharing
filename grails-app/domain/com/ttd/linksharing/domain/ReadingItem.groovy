package com.ttd.linksharing.domain

import org.hibernate.FetchMode

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]

    static namedQueries = {

        isReadForUser { Resource resource, User user ->
            projections {
                property('isRead')
            }
            eq 'user', user
            eq 'resource', resource

            fetchMode('user', FetchMode.JOIN)
        }

        unreadForUser { User user ->
            eq ('user', user)
            eq('isRead', Boolean.FALSE)

            fetchMode('resource', FetchMode.JOIN)
            fetchMode('resource.createdBy', FetchMode.JOIN)
        }
    }
}
