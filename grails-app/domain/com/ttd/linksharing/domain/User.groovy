package com.ttd.linksharing.domain

class User {

    String email
    String username
    String password
    String firstName
    String lastName
    Byte photo
    Boolean admin
    Boolean active
    Date dateCreated
    Date lastUpdated

    static hasMany = [topics: Topic]

    def scaffold = true

    static constraints = {
        email unique: true
        username unique: true
    }
}
