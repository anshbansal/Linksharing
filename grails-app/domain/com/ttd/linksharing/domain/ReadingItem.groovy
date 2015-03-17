package com.ttd.linksharing.domain

import org.hibernate.FetchMode

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]
}
