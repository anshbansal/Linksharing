package com.ttd.linksharing.domain

import com.ttd.linksharing.domain.User

class UserController {

    def userService

    def loginHandler() {
        session.username = params.username
        redirect controller: 'home', action: 'dashboard'
    }

    def profile() {
        User loggedUser = userService.forUsername(session.username)
        render view: "profile", model: [loggedUser: loggedUser]
    }
}
