package com.ttd.linksharing.domain

class ReadingItem {
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]
}
