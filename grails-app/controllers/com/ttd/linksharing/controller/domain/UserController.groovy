package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.user.PasswordCO
import com.ttd.linksharing.co.user.UserDetailsCO
import com.ttd.linksharing.domain.User

class UserController {

    def userService

    def loginHandler() {
        session.loggedUser = User.findByUsername(params?.username)
        redirect controller: 'home', action: 'dashboard'
    }

    def profile() {
        render view: "profile", model: [loggedUser: session?.loggedUser]
    }

    def isUniqueIdentifierValid() {
        String uniqueIdentifier = params.username ?: params.email
        render isValidUniqueIdentifier(uniqueIdentifier) ? "true" : "false"
    }

    def updateDetails(UserDetailsCO userDetailsCO) {

        //TODO Field error in photo object. Fix.
        println "Aseem before hasErrors ${userDetailsCO.errors}"

        if (userDetailsCO.hasErrors()) {
            redirect action: "profile"
            return
        }
        if (isValidUniqueIdentifier(userDetailsCO.email)) {
            userService.updateDetails(userDetailsCO, session?.loggedUser)
        } else {
            flash['editProfileMessage'] = "Email is already used"
        }
        redirect action: "profile"
    }

    def updatePassword(PasswordCO passwordCO) {

        if (passwordCO.hasErrors()) {
//            flash.message = "Passwords do not match"
            render "Passwords do not match"
        } else {
            userService.updatePassword(session?.loggedUser, passwordCO.password)
            render "Password has been updated"
        }
    }

    def resetPassword(String uniqueIdentifier) {
        render userService.resetPasswordAndSendMail(uniqueIdentifier) ? "false" : "true"
    }

    private Boolean isValidUniqueIdentifier(String uniqueIdentifier) {
        if (session?.loggedUser) {
            return userService.isUniqueIdentifierValid(uniqueIdentifier, session?.loggedUser)
        } else {
            return userService.isUniqueIdentifierValid(uniqueIdentifier)
        }
    }
}
