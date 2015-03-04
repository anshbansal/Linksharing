package com.ttd.linksharing.service.util

import com.ttd.linksharing.domain.User
import grails.gsp.PageRenderer

class SendMailService {

    def asynchronousMailService
    PageRenderer groovyPageRenderer

    Boolean sendResetMail(User user, String newPassword) {
        asynchronousMailService.sendMail {
            to user.email
            subject "Your Password Reset Details"
            body groovyPageRenderer.render(view: "/templates/mail/_reset_password", model: [user: user, newPassword: newPassword])
        }
    }
}
