package com.ttd.linksharing.domain

import com.ttd.linksharing.co.user.LoginCredentials

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
        photo size: 0..576, nullable: true
    }

    @Override
    String toString() {
        "$username"
    }

    static namedQueries = {
        byIdentifier { String uniqueIdentifier ->
            or {
                eq 'email', uniqueIdentifier
                eq 'username', uniqueIdentifier
            }
        }

        withCredentials { LoginCredentials credentials ->
            byIdentifier credentials.uniqueIdentifier
            eq 'password', credentials.loginPassword
        }
    }
}