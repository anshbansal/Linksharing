package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness

class Subscription {

    User user
    Topic topic
    Seriousness seriousness
    Date dateCreated

    def afterInsert = {
        this.updateReadingLists()
    }

    static belongsTo = [user: User, topic: Topic]

    static constraints = {
        seriousness nullable: true
        topic unique: 'user'
    }

    def updateReadingLists = {
        topic.resources.each { Resource resource ->
            user.addToReadingsItems(
                    new ReadingItem(resource: resource, user: user)
            )
            save()
        }
    }
}
