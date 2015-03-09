package com.ttd.linksharing.controller

class UtilController {

    def renderPost() {
        def attrs = params + [type: "${params.actionTo}"]
        render ls.posts(attrs)
    }
}
