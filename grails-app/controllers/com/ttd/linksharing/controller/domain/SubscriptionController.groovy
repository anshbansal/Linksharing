package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic

class SubscriptionController {

    def subscriptionService

    def subscribeByUniqueToken(String uniqueToken) {
        if (subscriptionService.subscribeUserByInvitationToken(uniqueToken)) {
            render "You have been subscribed"
        } else {
            render "Invite Token is no longer valid"
        }
    }

    def subscribeToTopic(Topic topic) {
        Subscription subscription = new Subscription(topic: topic, user: session?.loggedUser)
        subscriptionService.save(subscription)
        render "Success"
    }

    def unsubscribeFromTopic(Topic topic) {
        subscriptionService.unsubscribeUserFromTopic(session?.loggedUser, topic)
        render "Success"
    }
}
