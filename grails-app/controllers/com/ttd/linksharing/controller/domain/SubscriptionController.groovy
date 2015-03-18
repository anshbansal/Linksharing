package com.ttd.linksharing.controller.domain

class SubscriptionController {

    def subscriptionService

    def subscribeByUniqueToken(String uniqueToken) {
        if (subscriptionService.subscribeUserByInvitationToken(uniqueToken)) {
            render "You have been subscribed"
        } else {
            render "Invite Token is no longer valid"
        }
    }
}
