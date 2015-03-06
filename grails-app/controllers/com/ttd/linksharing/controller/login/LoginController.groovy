package com.ttd.linksharing.controller.login

import com.ttd.linksharing.co.user.LoginCredentials
import com.ttd.linksharing.domain.User

class LoginController {

    def userService

    def index() {
        render view: "home"
    }

    def login(LoginCredentials credentials) {
        if (credentials.hasErrors()) {
            redirect action: "index"
            return
        }
        User user = userService.isValidUser(credentials)

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