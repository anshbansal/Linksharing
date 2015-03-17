package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User

class UserDetails {
    User user
    Long numSubscriptions
    Long numTopics

    Long getUserId() {
        user.id
    }

    static Closure<List<UserDetails>> mapFromSubscriptions = { List<Subscription> subscriptions ->
        List<UserDetails> usersDetail = []
        subscriptions.collect(usersDetail) { Subscription subscription ->
            new UserDetails(user: subscription.user)
        }
        usersDetail
    }
}
