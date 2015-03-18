package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.invitation.InvitationCO
import com.ttd.linksharing.domain.Invitation
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.util.Mappings
import grails.transaction.Transactional

@Transactional
class InvitationService {

    Invitation save(Invitation invitation, Boolean isFlushEnabled = false) {
        if (! invitation.save(flush: isFlushEnabled)) {
            return null
        }
        invitation
    }
    
    Invitation create(InvitationCO invitationCO) {
        User invitedUser = User.findByEmail(invitationCO.emailOfUser)
        String randomToken = Mappings.getRandomStringOfSize(20)
        Invitation invitation = new Invitation(invitedUser: invitedUser,
                                        invitationOfTopic: invitationCO.inviteTopic, randomToken: randomToken)
        save(invitation)
    }
}
