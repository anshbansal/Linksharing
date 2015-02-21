package com.ttd.linksharing.domain

class UserController {

    def loginHandler() {
        session.username = params.username
        forward controller: 'home', action: 'dashboard'
    }
}
