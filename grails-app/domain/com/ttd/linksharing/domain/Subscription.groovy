package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Seriousness

class Subscription {

    User user
    Topic topic
    Seriousness seriousness
    Date dateCreated

    def scaffold = true

    static constraints = {
    }
}
