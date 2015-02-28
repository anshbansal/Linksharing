package com.ttd.linksharing.login

import com.ttd.linksharing.co.LoginCO
import com.ttd.linksharing.domain.User

class LoginController {

    def userService

    def index() {
        render view: "login"
    }

    def login(LoginCO loginCO) {
        User user = userService.isValidUser(loginCO)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [user: user]
        } else {
            redirect action: "index"
        }
    }

    def logout() {
        session.invalidate()
        redirect action: "index"
    }
}