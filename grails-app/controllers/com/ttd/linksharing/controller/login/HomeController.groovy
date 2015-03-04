package com.ttd.linksharing.controller.login

import com.ttd.linksharing.domain.User

class HomeController {

    def userService

    def index() {
        if (session?.username) {
            forward action: 'dashboard'
        } else {
            redirect controller: 'login', action: 'index'
        }
    }

    def dashboard() {
        User loggedUser = userService.forUsername(session.username)

        render view: "dashboard", model: [loggedUser: loggedUser]
    }
}
