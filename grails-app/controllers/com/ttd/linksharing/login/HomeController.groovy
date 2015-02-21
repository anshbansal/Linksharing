package com.ttd.linksharing.login

class HomeController {

    def index() {
        //TODO Change required for session invalidate
        if (session?.username) {
            redirect action: 'dashboard'
        } else {
            redirect controller: 'login', action: 'index'
        }
    }

    def dashboard() {
        render session.username
    }
}
