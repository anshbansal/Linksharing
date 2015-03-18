package com.ttd.linksharing.service.util

import com.ttd.linksharing.domain.Invitation
import com.ttd.linksharing.domain.User
import grails.gsp.PageRenderer

class SendMailService {

    def grailsApplication
    def asynchronousMailService
    PageRenderer groovyPageRenderer

    Boolean sendResetMail(User user, String newPassword) {
        asynchronousMailService.sendMail {
            to user.email
            subject "Your Password Reset Details"
            html groovyPageRenderer.render(view: "/mail/reset_password", model: [user: user, newPassword: newPassword])
        }
    }

    Boolean sendInvitationMail(User user, Invitation invitation) {
        asynchronousMailService.sendMail {
            to user.email
            subject "Invitation to join"
            html groovyPageRenderer.render(view: "/mail/invitation", model: [user: user, invitation: invitation,
                                                                             baseUrl: grailsApplication.config.grails.serverURL])
        }
    }
}
