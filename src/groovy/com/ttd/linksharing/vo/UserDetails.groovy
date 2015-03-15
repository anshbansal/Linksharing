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

    static Closure mapFromSubscriptions = { List<Subscription> subscriptions ->
        subscriptions.collect([]) { Subscription subscription ->
            new UserDetails(user: subscription.user)
        }
    }
}
