package com.ttd.linksharing.co.user

import grails.validation.Validateable

@Validateable
class LoginCO {
    String uniqueIdentifier
    String loginPassword

    static constraints = {
        uniqueIdentifier blank: false
        loginPassword blank: false
    }
}