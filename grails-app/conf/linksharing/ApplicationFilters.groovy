package linksharing

class ApplicationFilters {

    def filters = {
        all(controllerExclude: 'assets', action: '*') {
            before = {
                println "Request to ${controllerName}:${actionName} with parameters ${params}"
            }
        }

        beforeLogin(controller: '*', controllerExclude: "login|registration|assets|console|util") {
            before = {

                if (!session?.loggedUser) {

                    Boolean allow = Boolean.FALSE

                    switch (controllerName) {
                        case 'home':
                            allow = actionName in ['home']
                            break
                        case 'user':
                            allow =  actionName in ['loginHandler', 'show', 'isUniqueIdentifierValid', 'photo']
                            break
                    }

                    if (allow) {
                        return true
                    }

                    println "Request to ${controllerName}:${actionName} filtered as user not logged in"
                    flash['loginMessage'] = 'Login to the System'
                    redirect controller: "home", action: "home"
                    return false
                }
            }
        }
    }

}
