package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

class Topic {

    String name
    User createdBy
    Date dateCreated
    Date lastUpdated
    Visibility scope

    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription]

    def afterInsert = {
        Subscription self = new Subscription(user: createdBy, topic: this, dateCreated: dateCreated)
        withNewSession {
            self.save()
        }
    }

    static constraints = {
        name unique: 'createdBy'
    }

    @Override
    String toString() {
        "$name created by $createdBy"
    }
}
