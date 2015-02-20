package com.ttd.linksharing.domain

class ReadingItem {

    Resource resource
    User user
    Boolean isRead = Boolean.FALSE

    static belongsTo = [resource: Resource, user: User]
}
