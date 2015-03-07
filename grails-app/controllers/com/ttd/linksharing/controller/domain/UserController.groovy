package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.user.PasswordCO
import com.ttd.linksharing.co.user.UserDetailsCO
import com.ttd.linksharing.domain.User

class UserController {

    def userService

    def beforeInterceptor = [action: this.&adminAuth, only: 'users']

    def adminAuth() {
        if (! session?.loggedUser?.admin) {
            redirect controller: "home"
            return false
        }
    }

    def users() {}

    def loginHandler() {
        session.loggedUser = User.findByUsername(params?.username)
        redirect controller: 'home', action: 'dashboard'
    }

    def profile() {
        render view: "profile", model: [loggedUser: session?.loggedUser]
    }

    def isUniqueIdentifierValid() {
        String uniqueIdentifier = params.username ?: params.email
        render userService.isUniqueIdentifierValid(uniqueIdentifier, session?.loggedUser) ? "true" : "false"
    }

    def updateDetails(UserDetailsCO userDetailsCO) {
        if (userDetailsCO.hasErrors()) {
            //TODO Field error in photo object. Fix.
            println "Aseem before hasErrors ${userDetailsCO.errors}"

        } else if (! userService.updateDetails(userDetailsCO, session?.loggedUser)) {
            flash['editProfileMessage'] = "Email is already used"
        }

        redirect action: "profile"
    }

    def updatePassword(PasswordCO passwordCO) {

        if (passwordCO.hasErrors()) {
            render "Passwords do not match"
        } else {
            userService.updatePassword(session?.loggedUser, passwordCO.password)
            render "Password has been updated"
        }
    }

    def resetPassword(String uniqueIdentifier) {
        render userService.resetPasswordAndSendMail(uniqueIdentifier) ? "false" : "true"
    }
}
