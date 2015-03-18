package com.ttd.linksharing.controller.domain

class SubscriptionController {
    def index() {
        render view: "send_invite"
    }

    def create() {
        render "Reached inside subscription controller"
    }
}
