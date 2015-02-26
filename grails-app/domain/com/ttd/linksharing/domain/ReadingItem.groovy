package com.ttd.linksharing.domain

import org.hibernate.FetchMode

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]

    static namedQueries = {

        unReadForUser { User user ->
            unRead()
            eq('user', user)
        }

        unreadForUserName { String username ->
            unRead()
            'user' {
                eq('username',username)
            }
        }

        unRead {
            projections {
                property('resource')
            }
            eq('isRead', Boolean.FALSE)
            fetchMode('resource', FetchMode.JOIN)
        }
    }
}
