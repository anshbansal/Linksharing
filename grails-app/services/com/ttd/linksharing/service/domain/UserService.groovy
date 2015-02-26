package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class UserService {

    def save(User user, Boolean isFlushEnabled = false) {

        if (! user.save(flush: isFlushEnabled)) {
            return null
        }
        user
    }
}
