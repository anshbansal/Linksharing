package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness

class Subscription {

    User user
    Topic topic
    Seriousness seriousness
    Date dateCreated

    def afterInsert = {
        withNewSession {
            //Adding all existing resources to reading items of user
            Resource.findAllWhere(topic: this.topic).each { Resource resource ->
                new ReadingItem(resource: resource, user: this.user).save()
            }
        }
    }

    static belongsTo = [user: User, topic: Topic]

    static constraints = {
        seriousness nullable: true
        topic unique: 'user'
    }
}
