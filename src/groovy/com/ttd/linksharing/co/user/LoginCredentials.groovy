package com.ttd.linksharing.co.user

import grails.validation.Validateable

@Validateable
class LoginCredentials {
    String uniqueIdentifier
    String loginPassword

    static constraints = {
        uniqueIdentifier blank: false
        loginPassword blank: false
    }
}