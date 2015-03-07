package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.user.LoginCredentials
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

        if (!user.save(flush: isFlushEnabled)) {
            return null
        }
        user
    }

    User registerUser(RegistrationCO registrationCO) {
        if (!isUniqueIdentifierValid(registrationCO.username) && isUniqueIdentifierValid(registrationCO.email)) {
            return null
        }
        save(registrationCO.user)
    }

    @NotTransactional
    User isValidUser(LoginCredentials credentials) {
        User.withCredentials(credentials).get()
    }

    Boolean updatePassword(User user, String newPassword) {
        user.setPassword(newPassword)
        save(user)
        user.hasErrors()
    }

    Boolean isUniqueIdentifierValid(String newIdentifier, User user = null) {

        def criteria = {}
        if (user) {
            criteria = { not { idEq(user.id) } }
        }

        User.byIdentifier(newIdentifier).count(criteria) == 0
    }

    User updateDetails(UserDetailsCO userDetailsCO, User user) {

        if (!isUniqueIdentifierValid(userDetailsCO.email, user)) {
            return null
        }

        user.firstName = userDetailsCO.firstName
        user.lastName = userDetailsCO.lastName
        user.email = userDetailsCO.email
        user.photo = userDetailsCO.photo
        save(user)
    }

    def resetPasswordAndSendMail(String uniqueIdentifier) {
        User user = User.byIdentifier(uniqueIdentifier).get()
        String newPassword = resetPassword(user)

        sendMailService.sendResetMail(user, newPassword)
    }

    String resetPassword(User user) {
        String newPassword = RandomStringUtils.random(20, true, true)

        user.password = newPassword
        save(user)

        return newPassword
    }
}
