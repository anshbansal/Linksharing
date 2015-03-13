package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.resource.LinkResourceCO

class LinkResourceController {

    def linkResourceService

    def index() {
        render view: "create"
    }

    def create(LinkResourceCO linkResourceCO) {
        if (linkResourceCO.hasErrors()) {
            render "Details had errors ${linkResourceCO.errors}"
        } else if (linkResourceService.create(linkResourceCO, session?.loggedUser)) {
            render "Link Created"
        } else {
            render "Error occurred"
        }
    }
}
