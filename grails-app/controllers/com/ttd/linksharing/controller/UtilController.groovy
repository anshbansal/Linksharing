package com.ttd.linksharing.controller

class UtilController {

    def renderPost() {
        def attrs = params + [type: "${params.postType}"]
        render ls.posts(attrs)
    }

    def renderTopic() {
        def attrs = params + [type: "${params.topicType}"]
        render ls.topics(attrs)
    }

    def renderUser() {
        def attrs = params + [type: "${params.userType}"]
        render ls.topics(attrs)
    }
}
