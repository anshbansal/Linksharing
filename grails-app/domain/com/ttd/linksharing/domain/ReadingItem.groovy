package com.ttd.linksharing.domain

class ReadingItem {

    Resource resource
    User user
    Boolean isRead

    def scaffold = true

    static constraints = {
    }
}
