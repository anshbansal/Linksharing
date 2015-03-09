package com.ttd.linksharing.controller.domain

class ReadingItemController {

    def inbox() {
        def attrs = params + [type: "inbox"]
        render ls.posts(attrs)
    }

}
