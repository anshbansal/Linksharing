package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

class Topic {

    String name
    User createdBy
    Date dateCreated
    Date lastUpdated
    Visibility scope = Visibility.PUBLIC

    def afterInsert = {
        addSubscription(createdBy)
    }

    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription]

    static constraints = {
        name unique: 'createdBy'
    }

    List<User> getSubscribedUsers() {
        return this.subscriptions*.user
    }

    void addSubscription(User user) {
        addToSubscriptions(
                new Subscription(user: user, topic: this)
        )
        save()
    }

    @Override
    String toString() {
        "$name created by $createdBy"
    }
}
