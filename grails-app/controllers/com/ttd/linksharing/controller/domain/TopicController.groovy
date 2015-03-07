package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.topic.TopicInfo

class TopicController {

    def topicService

    def index() {
        render view: "create"
    }

    def isTopicPresent() {
        String topicName = params.topicName
        Boolean isTopicPresent = topicService.isTopicPresentForUser(session?.loggedUser, topicName)

        render isTopicPresent ? "false" : "true"
    }

    def create(TopicInfo info) {
        if (info.hasErrors()) {
            render "Info had error ${info.errors}"
            return
        }

        //TODO Implement this
//        if (topicService.create(info, session?.loggedUser))
        render "Ajax call success"
    }
}