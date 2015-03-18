package com.ttd.linksharing.co.invitation

import com.ttd.linksharing.domain.Topic
import grails.validation.Validateable

@Validateable
class InvitationCO {
    String emailOfUser
    Topic inviteTopic
}
