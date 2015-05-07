package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.resource.DocumentResourceCO
import com.ttd.linksharing.domain.DocumentResource
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class DocumentResourceService {

    def resourceService

    DocumentResource create(DocumentResourceCO documentResourceCO, User user, String filePath) {
        resourceService.save(new DocumentResource(description: documentResourceCO.documentDescription,
                topic: documentResourceCO.documentTopic, createdBy: user, filePath: filePath,
                fileType: documentResourceCO.document.contentType))
    }
}
