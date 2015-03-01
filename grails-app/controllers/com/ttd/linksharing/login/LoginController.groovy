package com.ttd.linksharing.login

import com.ttd.linksharing.co.LoginCO
import com.ttd.linksharing.co.PasswordCO
import com.ttd.linksharing.domain.User

class LoginController {

    def userService

    def index() {
        render view: "login"
    }

    def login(LoginCO loginCO) {
        User user = userService.isValidUser(loginCO)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [username: user.username]
        } else {
            redirect action: "index"
        }
    }

    def logout() {
        session.invalidate()
        redirect action: "index"
    }

    def updatePassword(PasswordCO passwordCO) {
        User loggedUser = userService.forUsername(session.username)

        if (passwordCO.hasErrors()) {
            flash.message = "Passwords do not match"
        } else {
            userService.updatePassword(loggedUser, passwordCO.password)
        }

        redirect controller: "user", action: "profile", model: [loggedUser: loggedUser]
    }
}