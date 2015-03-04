package com.ttd.linksharing.domain

class TopicController {

    def topicService

    def createTopic() {
        render template: "/templates/commons/create_topic", model: [title: "Create Topic"]
    }

    def isTopicPresent() {
        String topicName = params.topicName
        Boolean isTopicPresent = topicService.isTopicPresentForUser(session?.username, topicName)

        render isTopicPresent ? "false" : "true"
    }
}