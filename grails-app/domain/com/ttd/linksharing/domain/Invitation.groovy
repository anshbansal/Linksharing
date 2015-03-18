package com.ttd.linksharing.domain

class Invitation {
    String randomToken

    static belongsTo = [invitedUser: User, invitationOfTopic: Topic]

    static constraints = {
        randomToken size: 20..20
    }
}
