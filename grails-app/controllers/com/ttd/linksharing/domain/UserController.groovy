package com.ttd.linksharing.domain

class UserController {

    def loginHandler() {
        session.username = params.username
        redirect controller: 'home', action: 'dashboard'
    }
}
