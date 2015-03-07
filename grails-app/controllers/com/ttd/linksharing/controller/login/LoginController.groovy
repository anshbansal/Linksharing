package com.ttd.linksharing.controller.login

import com.ttd.linksharing.co.user.LoginCredentials
import com.ttd.linksharing.domain.User

class LoginController {

    static defaultAction = "home"

    def userService

    def home() {}

    def login(LoginCredentials credentials) {
        if (! credentials.hasErrors()) {
            User user = userService.getActiveUser(credentials)

            if (user) {
                forward controller: "user", action: "loginHandler", params: [username: user.username]
                return
            } else {
                flash['loginMessage'] = "Invalid Login Credentials"
            }
        }
        redirect action: defaultAction
    }

    def logout() {
        session.invalidate()
        redirect action: defaultAction
    }
}