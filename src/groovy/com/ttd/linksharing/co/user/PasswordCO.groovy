package com.ttd.linksharing.co.user

import grails.validation.Validateable

@Validateable
class PasswordCO {
    String password
    String rePassword

    static constraints = {
        password validator: { val, obj ->
            obj.rePassword == val
        }, blank: false
        rePassword blank: false
    }
}
