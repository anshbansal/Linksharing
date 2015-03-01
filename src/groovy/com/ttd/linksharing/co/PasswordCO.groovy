package com.ttd.linksharing.co

import grails.validation.Validateable

@Validateable
class PasswordCO {
    String password
    String rePassword

    static constraints = {
        password validator: { val, obj ->
            obj.rePassword == val
        }
    }
}
