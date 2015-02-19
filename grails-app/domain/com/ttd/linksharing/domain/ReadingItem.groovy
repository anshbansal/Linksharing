package com.ttd.linksharing.domain

class ReadingItem {

    Resource resource
    User user
    Boolean isRead

    static belongsTo = [resource: Resource, user: User]

    def scaffold = true

    static constraints = {
    }
}
