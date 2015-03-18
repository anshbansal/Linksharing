package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.invitation.InvitationCO
import com.ttd.linksharing.domain.User

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

        User invitedUser = User.findByEmail(invitationCO.emailOfUser)
        if ( subscriptionService.isUserSubscribedToTopic(invitedUser, invitationCO.inviteTopic)) {
            render "User is already subscribed to this topic"
            return
        }

        if (invitationService.createOrUpdateInvitationAndSendEmail(invitedUser, invitationCO.inviteTopic)) {
            render "Invitation Sent"
        } else {
            render "Error occurred"
        }
    }
}
