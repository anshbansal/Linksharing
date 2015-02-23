package com.ttd.linksharing.domain

class User {
    String email
    String username
    String password
    String firstName
    String lastName
    Byte[] photo
    Boolean admin  = Boolean.FALSE
    Boolean active = Boolean.TRUE
    Date dateCreated
    Date lastUpdated

    static hasMany = [
        topics: Topic,
        subscriptions: Subscription,
        ratings: ResourceRating,
        readingsItems: ReadingItem,
        resources: Resource
    ]

    static constraints = {
        email unique: true, email: true
        username unique: true
        lastName nullable: true
        photo size: 0..576, nullable: true
    }

    @Override
    String toString() {
        "$username"
    }
}
