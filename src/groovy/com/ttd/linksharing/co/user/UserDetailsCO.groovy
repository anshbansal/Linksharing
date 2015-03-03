package com.ttd.linksharing.co.user

import grails.validation.Validateable

@Validateable
class UserDetailsCO {
    String firstName
    String lastName
    String email
    Byte[] photo

    static constraints = {
        firstName blank: false
        lastName blank: false
        email blank: false, email: true
    }
}
