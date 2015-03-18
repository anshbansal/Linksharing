package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.invitation.InvitationCO

class InvitationController {

    def subscriptionService
    def invitationService

    def index() {
        render view: "send_invite"
    }

    def create(InvitationCO invitationCO) {

        if (! subscriptionService.isUserSubscribedToTopic(session?.loggedUser, invitationCO.inviteTopic)) {
            render "You are not authorized to create link for this topic"
            return
        }

        if (invitationService.create(invitationCO)) {
            render "Invitation Sent"
        } else {
            render "Invitation for this topic is already present for this user"
        }
    }
}
