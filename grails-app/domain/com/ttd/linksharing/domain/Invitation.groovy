package com.ttd.linksharing.domain

class Invitation {
    String randomToken
    Date lastUpdated

    static belongsTo = [invitedUser: User, topic: Topic]

    static constraints = {
        randomToken size: 20..20, unique: true
        topic unique: 'invitedUser'
    }

    static mapping = {
        version false
    }
}
