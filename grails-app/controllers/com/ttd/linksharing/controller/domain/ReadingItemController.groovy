package com.ttd.linksharing.controller.domain

class ReadingItemController {

    def inbox() {
        def attrs = params + [type: "${actionName}"]
        render ls.posts(attrs)
    }

}
