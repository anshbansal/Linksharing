package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User

class UserDetails {
    User user
    Integer numSubscriptions
    Integer numTopics

    static Closure mapFromSubscriptions = { List<Subscription> subscriptions ->
        subscriptions.collect([]) { Subscription subscription ->
            new UserDetails(user: subscription.user)
        }
    }
}
