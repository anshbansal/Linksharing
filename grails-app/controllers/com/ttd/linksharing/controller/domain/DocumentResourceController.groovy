package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.resource.DocumentResourceCO
import com.ttd.linksharing.domain.DocumentResource
import org.springframework.web.multipart.MultipartFile

class DocumentResourceController {

    def subscriptionService
    def documentResourceService

    def index() {
        render view: "create"
    }

    def create(DocumentResourceCO documentResourceCO) {
        if (!subscriptionService.isUserSubscribedToTopic(session?.loggedUser, documentResourceCO.documentTopic)) {
            render "You are not authorized to create link for this topic"
            return
        }

        String filePathWithoutDocumentRoot = "/${session?.loggedUser?.id}/${documentResourceCO.documentTopic?.id}/${documentResourceCO.document.originalFilename}"

        if (documentResourceCO.hasErrors()) {
            render "Details had errors ${documentResourceCO.errors}"
        } else if (documentResourceService.create(documentResourceCO, session?.loggedUser, filePathWithoutDocumentRoot)) {

            String documentRoot = grailsApplication.config.grails.linksharing.files.rootPath
            String filePathWithDocumentRoot = "${documentRoot}/${filePathWithoutDocumentRoot}"

            new File(filePathWithDocumentRoot).mkdirs()
            documentResourceCO.document.transferTo(new File(filePathWithDocumentRoot))
            render "Document Created"
        } else {
            render "Error occurred"
        }
    }

    def download(DocumentResource documentResource) {
        String documentRoot = grailsApplication.config.grails.linksharing.files.rootPath

        println "Docuemnt rot is ${documentRoot}"
        println "Path is ${documentRoot}/${documentResource.filePath}"

        MultipartFile file = new File("${documentRoot}/${documentResource.filePath}")
        response.contentType = file.contentType
        response.contentLength = file.bytes.size()

        OutputStream out = response.outputStream
        out.write(file.bytes)
        out.close()
    }
}
