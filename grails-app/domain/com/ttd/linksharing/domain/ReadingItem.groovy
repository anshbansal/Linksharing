package com.ttd.linksharing.domain

import org.hibernate.FetchMode

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]

    static namedQueries = {
        unreadForUser { User user ->
            eq ('user', user)
            eq('isRead', Boolean.FALSE)

            fetchMode('resource', FetchMode.JOIN)
            fetchMode('resource.createdBy', FetchMode.JOIN)
        }
        resourceDescriptionLike { String term ->
            'resource' {
                descriptionLike(term)
            }
        }
    }
}
