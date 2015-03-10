package com.ttd.linksharing.controller.login

class HomeController {

    def home() {
        if (session?.loggedUser) {
            forward action: 'dashboard'
        } else {
            render view: "home"
        }
    }

    def dashboard() {}
}
