package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

import static com.ttd.linksharing.util.ServiceUtil.validateAndSave

@Transactional
class UserService {

    User save(User user, Map args = [:]) {

        if (!validateAndSave(user, args)) {
            return null
        }
        user
    }
}
