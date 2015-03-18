package com.ttd.linksharing.controller.domain

class SubscriptionController {

    def subscribeByUniqueToken(String uniqueToken) {
        render "Hello with ${uniqueToken}"
    }
}
