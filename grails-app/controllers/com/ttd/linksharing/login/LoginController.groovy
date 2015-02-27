package com.ttd.linksharing.login

import com.ttd.linksharing.domain.User

class LoginController {

    def userService

    def index() {
        render view: "login"
    }

    def login() {
        User user = userService.isValidUser(params.uniqueIdentifier, params.loginPassword)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [user: user]
        } else {
            forward action: "index"
        }
    }

    def logout() {
        session.invalidate()
        redirect action: "index"
    }
}
