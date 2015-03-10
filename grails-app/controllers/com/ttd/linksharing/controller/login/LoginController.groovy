package com.ttd.linksharing.controller.login

import com.ttd.linksharing.co.user.LoginCredentials
import com.ttd.linksharing.domain.User

class LoginController {

    static defaultAction = "login"

    def userService

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
        redirect controller: "home", action: "home"
    }

    def logout() {
        session.invalidate()
        redirect controller: "home", action: "home"
    }
}