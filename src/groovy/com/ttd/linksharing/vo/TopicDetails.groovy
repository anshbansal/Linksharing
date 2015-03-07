package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User

class TopicDetails {
    Topic topic
    User creator
    Integer numSubscriptions
    Integer numResources
}
