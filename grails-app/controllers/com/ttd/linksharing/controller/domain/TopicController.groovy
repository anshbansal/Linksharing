package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.co.topic.TopicSeriousnessDetails
import com.ttd.linksharing.co.topic.TopicVisibility
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.enums.Seriousness
import com.ttd.linksharing.enums.Visibility

class TopicController {

    def topicService

    def index() {
        render view: "create"
    }

    def show(Topic topic) {
        render view: "show", model: [topic: topic]
    }

    def isTopicPresent() {
        String topicName = params.topicName
        Boolean isTopicPresent = topicService.isTopicPresentForUser(session?.loggedUser, topicName)

        render isTopicPresent ? "false" : "true"
    }

    def create(TopicInfo info) {
        if (info.hasErrors()) {
            render "Info had error ${info.errors}"
        } else if (topicService.create(info, session?.loggedUser)) {
            render "Topic created"
        } else {
            render "Error occurred"
        }
    }

    def updateTopicSeriousness(TopicSeriousnessDetails topicSeriousnessDetails) {
        render "Got seriousness details"
    }

    def updateTopicVisibility(TopicVisibility topicVisibility) {
        render "Got visibility details"
    }

    def delete(Long topicId) {
        topicService.deleteTopicById(topicId)
        render "Success"
    }

    def topics(String topicListType, Long userId) {
        render(view: "/templates/_topics", model: [topicListType: topicListType, userId: userId])
    }

    def updateTopicName(String newTopicName, Long topicId) {
        topicService.updateTopicNameById(newTopicName, topicId)
        render "success"
    }
}