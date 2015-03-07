package linksharing

class ApplicationFilters {

    def filters = {
        all(controllerExclude: 'assets', action: '*') {
            before = {
                println "Request to ${controllerName}:${actionName} with parameters ${params}"
            }
        }

        beforeLogin(controller: "*", controllerExclude: 'assets', action: "*", actionExclude: 'home|login*|register|isUniqueIdentifierValid') {
            before = {
                if (!session?.loggedUser) {
                    println "Request to ${controllerName}:${actionName} filtered as user not logged in"
                    flash['loginMessage'] = 'Login to the System'
                    redirect controller: "login"
                    return false
                }
            }
        }
    }

}
