package com.ttd.linksharing.domain

abstract class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [readingItem: ReadingItem, ratings: ResourceRating]

    static mapping = {
        tablePerHierarchy false
    }
}
