package com.ttd.linksharing.domain

class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated

    static belongsTo = [topic: Topic]

    static constraints = {
    }
}
