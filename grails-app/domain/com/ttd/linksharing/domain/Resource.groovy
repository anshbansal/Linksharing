package com.ttd.linksharing.domain

abstract class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated

    def afterInsert = {
        withNewSession {
            //Adding new resource to reading items of subscribed users
            Subscription.findAllWhere(topic: this.topic).each { Subscription subscription ->
                new ReadingItem(resource: this, user: subscription.user).save()
            }
        }
    }

    static belongsTo = [createdBy: User, topic: Topic]
    static hasMany = [readingItem: ReadingItem, ratings: ResourceRating]

    static mapping = {
        tablePerHierarchy false
    }
}
