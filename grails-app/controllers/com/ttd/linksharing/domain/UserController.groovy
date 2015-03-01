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

    def isUniqueIdentifierValid() {

        String uniqueIdentifier = params.username ?: params.email
        Boolean isValid = false
        if (session?.username) {
            isValid = userService.isUniqueIdentifierValid(uniqueIdentifier, session?.username)
        } else {
            isValid = userService.isUniqueIdentifierValid(uniqueIdentifier)
        }
        render isValid ? "true" : "false"
    }

    def updateDetails() {
        //TODO Implement this
        println (">>>" * 30)
    }
}
