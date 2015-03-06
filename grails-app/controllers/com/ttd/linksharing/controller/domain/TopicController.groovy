package com.ttd.linksharing.controller.domain

class TopicController {

    def topicService

    def index() {
        render view: "create"
    }

    def isTopicPresent() {
        String topicName = params.topicName
        Boolean isTopicPresent = topicService.isTopicPresentForUser(session?.username, topicName)

        render isTopicPresent ? "false" : "true"
    }

    def create() {
        render "Ajax call success"
    }
}