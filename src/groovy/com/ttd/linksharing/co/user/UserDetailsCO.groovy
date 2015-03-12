package com.ttd.linksharing.co.user

import com.ttd.linksharing.domain.User
import grails.validation.Validateable

@Validateable
class UserDetailsCO {
    String firstName
    String lastName
    String email

    byte[] photo
    String avatarType

    static constraints = {
        importFrom User, include: ['photo', 'avatarType']

        firstName blank: false
        lastName blank: false
        email email: true
    }
}
