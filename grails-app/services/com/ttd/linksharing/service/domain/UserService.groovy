package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.LoginCO
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
    User isValidUser(LoginCO loginCO) {
        User.createCriteria().get {
            or {
                eq 'email', loginCO.uniqueIdentifier
                eq 'username', loginCO.uniqueIdentifier
            }
            eq 'password', loginCO.loginPassword
        }
    }
}
