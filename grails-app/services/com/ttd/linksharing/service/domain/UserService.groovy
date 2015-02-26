package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
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
