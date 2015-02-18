package com.ttd.linksharing.domain

import com.ttd.linksharing.enums.Visibility

class Topic {

    String name
    User createdBy
    Date dateCreated
    Date lastUpdated
    Visibility scope

    static hasMany = [resources: Resource]

    def scaffold = true

    static constraints = {
    }
}
