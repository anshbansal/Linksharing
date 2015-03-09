package com.ttd.linksharing.controller

class UtilController {

    def renderPost() {
        def attrs = params + [type: "${params.actionTo}"]
        render ls.posts(attrs)
    }

    def renderTopic() {
        def attrs = params + [type: "${params.actionTo}"]
        render ls.topics(attrs)
    }
}
