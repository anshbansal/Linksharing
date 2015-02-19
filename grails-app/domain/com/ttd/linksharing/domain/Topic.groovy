package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

class Topic {

    String name
    User createdBy
    Date dateCreated
    Date lastUpdated
    Visibility scope

    static belongsTo = [createdBy: User]
    static hasMany = [resources: Resource]

    def scaffold = true

    def afterInsert = {
        Subscription self = new Subscription(user: createdBy, topic: this,
                                                dateCreated: dateCreated)
        self.save()
    }

    static constraints = {
        name unique: 'createdBy'
    }
}
