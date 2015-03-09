package com.ttd.linksharing.controller.domain

class ResourceController {

    def recentShares() {
        def attrs = params + [type: "${actionName}"]
        render ls.posts(attrs)
    }
}
