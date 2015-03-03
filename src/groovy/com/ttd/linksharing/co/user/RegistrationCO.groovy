package com.ttd.linksharing.co.user

import com.ttd.linksharing.domain.User

class RegistrationCO {
    String email
    String username
    String password
    String firstName
    String lastName
    Byte[] photo

    User getUser() {
        new User(
            email: email,
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            photo: photo
        )
    }
}
