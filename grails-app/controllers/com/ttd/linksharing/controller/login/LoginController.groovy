package com.ttd.linksharing.controller.login

import com.ttd.linksharing.co.user.LoginCO
import com.ttd.linksharing.co.user.PasswordCO
import com.ttd.linksharing.domain.User

class LoginController {

    def userService

    def index() {
        render view: "home"
    }

    def login(LoginCO loginCO) {
        if (loginCO.hasErrors()) {
            redirect action: "index"
            return
        }
        User user = userService.isValidUser(loginCO)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [username: user.username]
        } else {
            flash['loginMessage'] = "Invalid Login Credentials"
            redirect action: "index"
        }
    }

    def logout() {
        session.invalidate()
        redirect action: "index"
    }
}