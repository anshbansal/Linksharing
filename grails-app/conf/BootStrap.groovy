class BootStrap {

    def bootstrapService

    def init = { servletContext ->
        bootstrapService.init()
    }

    def destroy = {
    }
}