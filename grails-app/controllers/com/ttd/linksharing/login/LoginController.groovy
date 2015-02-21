package com.ttd.linksharing.login

import com.ttd.linksharing.domain.User

class LoginController {

    def index() {
        render view: "login"
    }

    def login() {
        //TODO Change below for email or username
        User user = User.findWhere(email: params.email, password: params.password)

        if (user) {
            forward controller: "user", action: "loginHandler", params: [username: user?.username]
        } else {
            forward action: "index"
        }
    }
}
