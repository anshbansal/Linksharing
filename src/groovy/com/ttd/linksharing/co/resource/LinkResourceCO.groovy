package com.ttd.linksharing.co.resource

import com.ttd.linksharing.domain.LinkResource
import com.ttd.linksharing.domain.Topic
import grails.validation.Validateable

@Validateable
class LinkResourceCO {

    String linkDescription
    String linkUrl
    Topic linkTopics

    static constraints = {

    }
}
