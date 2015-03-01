package com.ttd.linksharing.login

import com.ttd.linksharing.domain.User

class HomeController {

    def userService

    def index() {
        //TODO Change required for session invalidate
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
