package linksharing

class ApplicationFilters {

    def filters = {
        all(controllerExclude: 'assets', action:'*') {
            before = {
                println "Request to ${controllerName}:${actionName} with parameters ${params}"
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
