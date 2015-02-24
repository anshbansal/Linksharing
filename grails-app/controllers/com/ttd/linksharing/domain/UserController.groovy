package com.ttd.linksharing.domain

class UserController {

    def loginHandler() {
        session.user = params.user
        redirect controller: 'home', action: 'dashboard'
    }
}
