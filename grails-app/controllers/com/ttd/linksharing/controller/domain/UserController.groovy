package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.user.PasswordCO
import com.ttd.linksharing.co.user.UserDetailsCO
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.util.Mappings

class UserController {

    def userService

    def beforeInterceptor = [action: this.&adminAuth, only: 'users']

    def adminAuth() {
        if (! session?.loggedUser?.admin) {
            redirect controller: "home", action: "home"
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

    def photo(User user) {
        response.contentType = user.avatarType
        response.contentLength = user.photo.size()
        OutputStream out = response.outputStream
        out.write(user.photo)
        out.close()
    }

    def show(User user) {
        render view: "public_profile", model: [user: user]
    }

    def isUniqueIdentifierValid() {
        String uniqueIdentifier = params.username ?: params.email
        render userService.isUniqueIdentifierValid(uniqueIdentifier, session?.loggedUser) ? "true" : "false"
    }

    def isEmailPresent(String emailOfUser) {
        render userService.isEmailPresent(emailOfUser) ? "true" : "false"
    }

    def updateDetails(UserDetailsCO userDetailsCO) {

        Mappings.setScaledImage(request.getFile('photo'), userDetailsCO)

        if (userDetailsCO.hasErrors()) {
            println "Aseem before hasErrors ${userDetailsCO.errors}"

        } else if (! userService.updateDetails(userDetailsCO, session?.loggedUser?.id)) {
            flash['editProfileMessage'] = "Email is already used"
        } else {
            session?.loggedUser = User.get(session?.loggedUser?.id)
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
