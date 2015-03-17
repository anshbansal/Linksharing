package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness
import com.ttd.linksharing.enums.Visibility

class Subscription {
    Seriousness seriousness = Seriousness.SERIOUS
    Date dateCreated

    static belongsTo = [user: User, topic: Topic]

    static constraints = {
        seriousness nullable: true
        topic unique: 'user'
    }
}
