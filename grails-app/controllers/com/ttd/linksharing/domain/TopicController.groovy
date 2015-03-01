package com.ttd.linksharing.domain

class TopicController {
    def createTopic() {
        render template: "/templates/commons/create_topic", model: [title: "Create Topic"]
    }
}
