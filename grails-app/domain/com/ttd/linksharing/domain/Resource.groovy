package com.ttd.linksharing.domain

abstract class Resource {

    String description
    Date dateCreated
    Date lastUpdated

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [readingItem: ReadingItem, ratings: ResourceRating]

    static constraints = {
        description unique: 'topic'
    }

    static mapping = {
        tablePerHierarchy false
    }

    List<User> getSubscribedUsers() {
        return topic.subscribedUsers
    }
}
