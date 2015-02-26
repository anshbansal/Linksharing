package com.ttd.linksharing.domain

import org.hibernate.FetchMode

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]

    static namedQueries = {

        isReadForUserName { Resource resource, String username ->
            projections {
                property('isRead')
            }
            'user' {
                eq 'username', username
            }
            eq 'resource', resource

            fetchMode('user', FetchMode.JOIN)
        }

        unreadForUserName { String username ->
            projections {
                property('resource')
            }

            'user' {
                eq('username',username)
            }
            eq('isRead', Boolean.FALSE)

            fetchMode('resource', FetchMode.JOIN)
        }
    }
}
