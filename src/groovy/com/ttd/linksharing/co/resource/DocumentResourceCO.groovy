package com.ttd.linksharing.co.resource

import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

@Validateable
class DocumentResourceCO {
    MultipartFile document
    String documentDescription
    Topic documentTopic
}
