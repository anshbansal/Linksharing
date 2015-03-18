package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.domain.Topic

class SubscriptionController {
    def index() {
        render view: "send_invite"
    }

    def create(String emailToInvite, Topic inviteTopic) {


        println " found email ${emailToInvite} and ${inviteTopic}"

        render "Reached inside subscription controller"
    }
}
