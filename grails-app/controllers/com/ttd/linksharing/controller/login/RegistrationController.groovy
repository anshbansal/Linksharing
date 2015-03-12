package com.ttd.linksharing.controller.login

import com.ttd.linksharing.co.user.RegistrationCO
import com.ttd.linksharing.domain.User

class RegistrationController {

    static defaultAction = "register"

    def userService

    def register(RegistrationCO registrationCO) {

        def f = request.getFile('photo')
        registrationCO.photo = f.bytes
        registrationCO.avatarType = f.contentType
        registrationCO.validate()
        if (registrationCO.hasErrors()) {
            println "Errors in registrationCO ${registrationCO.errors}"
            flash['registrationMessage'] = "Invalid Registration Credentials"
            redirect controller: "home", action: "home"
        }

        User user = userService.registerUser(registrationCO)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [username: user.username]
        } else {
            flash['registrationMessage'] = "Invalid Registration Credentials"
            redirect controller: "home", action: "home"
        }
    }
}
