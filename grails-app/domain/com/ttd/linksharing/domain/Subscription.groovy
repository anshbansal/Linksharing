package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness

class Subscription {
    Seriousness seriousness
    Date dateCreated

    static belongsTo = [user: User, topic: Topic]

    static constraints = {
        seriousness nullable: true
        topic unique: 'user'
    }
}
