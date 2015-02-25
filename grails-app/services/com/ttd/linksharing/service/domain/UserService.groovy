package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
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

    User findByUsername(String username) {
        User.findByUsername(username)
    }

    List<Topic> getSubscribedTopicsForUser(String username) {

        Subscription.findAllWhere(
                user: findByUsername(username),
                [sort: 'dateCreated', order: 'desc', max: 5]
        )*.topic
    }
}
