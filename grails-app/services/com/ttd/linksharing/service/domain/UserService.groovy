package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class UserService {

    User save(User user, Boolean isFlushEnabled = false, Boolean failOnError = false) {
        if(user.validate() && user.save(flush: isFlushEnabled, failOnError: failOnError)) {

        } else {
            user = null
        }
        user
    }
}
