package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

class Topic {

    String name
    User createdBy
    Date dateCreated
    Date lastUpdated
    Visibility scope = Visibility.PUBLIC

    def afterInsert = {
        withNewSession {
            //Subscribing Owner to the topic
            new Subscription(
                    user: createdBy,
                    topic: this
            )?.save()
        }
    }

    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource, subscriptions: Subscription]

    static constraints = {
        name unique: 'createdBy'
    }

    @Override
    String toString() {
        "$name created by $createdBy"
    }
}
