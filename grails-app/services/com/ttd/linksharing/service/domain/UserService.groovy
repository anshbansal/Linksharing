package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.user.LoginCO
import com.ttd.linksharing.co.user.RegistrationCO
import com.ttd.linksharing.co.user.UserDetailsCO
import com.ttd.linksharing.domain.User
import grails.transaction.NotTransactional
import grails.transaction.Transactional

import org.apache.commons.lang.RandomStringUtils

@Transactional
class UserService {

    def sendMailService

    User save(User user, Boolean isFlushEnabled = false) {

        if (! user.save(flush: isFlushEnabled)) {
            return null
        }
        user
    }

    User registerUser(RegistrationCO registrationCO) {
        if (! isUniqueIdentifierValid(registrationCO.username) && isUniqueIdentifierValid(registrationCO.email)) {
            return null
        }
        save(registrationCO.user)
    }

    @NotTransactional
    User isValidUser(LoginCO loginCO) {
        User.withCredentials(loginCO).get()
    }

    @NotTransactional
    User forUsername(String username) {
        User.findByUsername(username)
    }

    Boolean updatePassword(User user, String newPassword) {
        user.setPassword(newPassword)
        save(user)
        user.hasErrors()
    }

    Boolean isUniqueIdentifierValid(String newIdentifier, String loggedUserName) {

        User user = forUsername(loggedUserName)

        User.createCriteria().count() {
            not {
                idEq(user.id)
            }
            or {
                eq 'username', newIdentifier
                eq 'email', newIdentifier
            }
        } == 0
    }

    Boolean isUniqueIdentifierValid(String newIdentifier) {
        User.byIdentifier(newIdentifier).count() == 0
    }

    User updateDetails(UserDetailsCO userDetailsCO, String username) {
        User user = User.findByUsername(username)
        user.firstName = userDetailsCO.firstName
        user.lastName = userDetailsCO.lastName
        user.email = userDetailsCO.email
        user.photo = userDetailsCO.photo
        save(user)
    }

    def resetPasswordAndSendMail(String uniqueIdentifier) {
        String newPassword = resetPassword(uniqueIdentifier)
        User user = User.byIdentifier(uniqueIdentifier).get()

        sendMailService.sendResetMail(user, newPassword)
    }

    String resetPassword(String uniqueIdentifier) {
        String newPassword = RandomStringUtils.random(20, true, true)

        User user = User.byIdentifier(uniqueIdentifier).get()
        user.password = newPassword
        save(user)

        return  newPassword
    }
}
