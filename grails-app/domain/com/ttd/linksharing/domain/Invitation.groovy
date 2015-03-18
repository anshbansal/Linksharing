package com.ttd.linksharing.domain

class Invitation {
    String randomToken
    Date dateCreated

    static belongsTo = [invitedUser: User, invitationOfTopic: Topic]

    static constraints = {
        randomToken size: 20..20
        invitationOfTopic unique: 'invitedUser'
    }

    static mapping = {
        version false
    }
}
