package linksharing

class ApplicationFilters {

    static Map<String, String> BEFORE_LOGIN = [
            'user': 'isUniqueIdentifierValid',
            'util': 'renderPost'
    ]

    def filters = {
        all(controllerExclude: 'assets', action: '*') {
            before = {
                println "Request to ${controllerName}:${actionName} with parameters ${params}"
            }
        }

        beforeLogin(controller: "*", controllerExclude: 'assets|console',
                action: "*", actionExclude: 'home|login*|register') {
            before = {
                if (BEFORE_LOGIN[controllerName] == actionName) {
                    return true
                }
                if (!session?.loggedUser) {
                    println "Request to ${controllerName}:${actionName} filtered as user not logged in"
                    flash['loginMessage'] = 'Login to the System'
                    redirect controller: "home"
                    return false
                }
            }
        }
    }

}
