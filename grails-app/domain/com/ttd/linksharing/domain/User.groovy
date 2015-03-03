package com.ttd.linksharing.domain

import com.ttd.linksharing.co.user.LoginCO

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
        isUniqueIdentifierUsed { String uniqueIdentifier ->
            or {
                eq 'email', uniqueIdentifier
                eq 'username', uniqueIdentifier
            }
        }

        isValidUser { LoginCO loginCO ->
            isUniqueIdentifierUsed loginCO.uniqueIdentifier
            eq 'password', loginCO.loginPassword
        }
    }
}
