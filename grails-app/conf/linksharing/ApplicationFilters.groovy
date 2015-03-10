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

                    switch (controllerName) {
                        case 'home':
                            return actionName in ['home']
                        case 'user':
                            return actionName in ['loginHandler', 'show', 'isUniqueIdentifierValid']
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
