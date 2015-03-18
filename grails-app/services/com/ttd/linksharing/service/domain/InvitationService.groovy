package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Invitation
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.util.Mappings
import grails.transaction.Transactional

@Transactional
class InvitationService {

    def sendMailService

    Invitation save(Invitation invitation, Boolean isFlushEnabled = false) {
        if (!invitation.save(flush: isFlushEnabled)) {
            return null
        }
        invitation
    }

    Invitation createOrUpdateInvitationAndSendEmail(User invitedUser, Topic inviteTopic) {
        Invitation invitation = createOrUpdateInvitation(invitedUser, inviteTopic)
        sendMailService.sendInvitationMail(invitedUser, invitation)
        invitation
    }

    Invitation createOrUpdateInvitation(User invitedUser, Topic inviteTopic) {
        Invitation invitation = Invitation.findByInvitedUserAndTopic(invitedUser, inviteTopic)
        if (!invitation) {
            String randomToken = getUniqueToken()
            invitation = new Invitation(invitedUser: invitedUser, topic: inviteTopic, randomToken: randomToken)
        }
        save(invitation)
    }

    String getUniqueToken() {
        String randomToken = Mappings.getRandomStringOfSize(20)
        while (isTokenUsed(randomToken)) {
            randomToken = Mappings.getRandomStringOfSize(20)
        }
        randomToken
    }

    Boolean isTokenUsed(String token) {
        Invitation.countByRandomToken(token) != 0
    }
}
