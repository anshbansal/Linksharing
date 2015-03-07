package com.ttd.linksharing.controller.login

class HomeController {

    static defaultAction = "home"

    def home() {
        if (session?.loggedUser) {
            forward action: 'dashboard'
        } else {
            redirect controller: 'login'
        }
    }

    def dashboard() {}
}
