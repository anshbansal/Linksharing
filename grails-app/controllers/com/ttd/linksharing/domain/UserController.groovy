package com.ttd.linksharing.domain


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

    def isUniqueIdentifierValid(String username) {

        Boolean isValid = userService.isUniqueIdentifierValid(session.username, username)
        println (">>>" * 30)
        println isValid
        render isValid ? "true" : "false"
    }

    def updateDetails() {
        //TODO Implement this
        println (">>>" * 30)
    }
}
