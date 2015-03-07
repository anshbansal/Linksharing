package com.ttd.linksharing.co.topic

import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.enums.Visibility
import grails.validation.Validateable

@Validateable
class TopicInfo {
    String topicName
    Visibility topicVisibility
}
