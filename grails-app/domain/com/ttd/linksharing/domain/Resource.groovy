package com.ttd.linksharing.domain

abstract class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated

    def afterInsert = {
        this.updateReadingLists()
    }

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [readingItem: ReadingItem, ratings: ResourceRating]

    static mapping = {
        tablePerHierarchy false
    }

    def updateReadingLists = {
        topic.subscribedUsers.each { User subscribedUser ->
            subscribedUser.addToReadingsItems(
                    new ReadingItem(resource: this, user: subscribedUser)
            )
            save()
        }
    }
}
