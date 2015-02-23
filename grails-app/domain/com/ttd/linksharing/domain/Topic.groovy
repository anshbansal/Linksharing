package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

class Topic {
    String name
    Date dateCreated
    Date lastUpdated
    Visibility scope = Visibility.PUBLIC

    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription]

    static constraints = {
        name unique: 'createdBy'
    }

    List<User> getSubscribedUsers() {
        return this.subscriptions*.user
    }

    @Override
    String toString() {
        "$name created by $createdBy"
    }
}
