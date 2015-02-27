package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.User
import grails.transaction.NotTransactional
import grails.transaction.Transactional

@Transactional
class UserService {

    def save(User user, Boolean isFlushEnabled = false) {

        if (! user.save(flush: isFlushEnabled)) {
            return null
        }
        user
    }

    @NotTransactional
    User isValidUser(String uniqueIdentifier, String password) {
        User.createCriteria().get {
            or {
                eq 'email', uniqueIdentifier
                eq 'username', uniqueIdentifier
            }
            eq 'password', password
        }
    }
}
