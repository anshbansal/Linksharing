package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.resource.LinkResourceCO
import com.ttd.linksharing.domain.LinkResource
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class LinkResourceService {

    def resourceService

    LinkResource create(LinkResourceCO linkResourceCO, User user) {
        resourceService.save(new LinkResource(url: linkResourceCO.linkUrl,
                description: linkResourceCO.linkDescription,
                topic: linkResourceCO.linkTopics, createdBy: user))
    }
}
