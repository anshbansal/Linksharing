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
        User.isValidUser(loginCO).get()
    }

    @NotTransactional
    User forUsername(String username) {
        User.findByUsername(username)
    }

    Boolean updatePassword(User user, String newPassword) {
        user.setPassword(newPassword)
        save(user)
        user.hasErrors()
    }

    Boolean isUniqueIdentifierValid(String newIdentifier, String loggedUserName) {

        User user = forUsername(loggedUserName)
        if (! user) {
            return true
        }

        User.createCriteria().count() {
            not {
                eq 'id', user.id
            }
            or {
                eq 'username', newIdentifier
                eq 'email', newIdentifier
            }
        } == 0
    }

    Boolean isUniqueIdentifierValid(String newIdentifier) {
        User.isUniqueIdentifierUsed(newIdentifier).count() == 0
    }
}
