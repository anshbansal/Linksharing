package com.ttd.linksharing.login

import com.ttd.linksharing.co.user.RegistrationCO
import com.ttd.linksharing.domain.User

class RegistrationController {

    def userService

    def register(RegistrationCO registrationCO) {
        User user = userService.registerUser(registrationCO)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [username: user.username]
        } else {
            flash['registrationMessage'] = "Invalid Registration Credentials"
            redirect action: "index"
        }
    }
}
