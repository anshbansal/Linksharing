package com.ttd.linksharing.co.topic

import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.enums.Seriousness
import grails.validation.Validateable

@Validateable
class TopicSeriousnessDetails {
    Topic topic
    Seriousness newSeriousness
}
