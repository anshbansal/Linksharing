package com.ttd.linksharing.co.user

import com.ttd.linksharing.domain.User
import grails.validation.Validateable

@Validateable
class RegistrationCO {

    String username

    String firstName
    String lastName
    String email

    String password
    String rePassword

    byte[] photo
    String avatarType

    User getUser() {
        if (photo.size() > 0) {
            new User(email: email, username: username, password: password,
                    firstName: firstName, lastName: lastName, photo: photo,
                    avatarType: avatarType)
        } else {
            new User(email: email, username: username, password: password,
                    firstName: firstName, lastName: lastName)
        }
    }

    static constraints = {
        importFrom UserDetailsCO
        importFrom PasswordCO

        username blank: false
    }
}
