package com.ttd.linksharing.controller.domain

import com.ttd.linksharing.co.resource.DocumentResourceCO

class DocumentResourceController {

    def subscriptionService
    def documentResourceService

    def index() {
        render view: "create"
    }

    def create(DocumentResourceCO documentResourceCO) {
        if (!subscriptionService.isUserSubscribedToTopic(session?.loggedUser, documentResourceCO.documentTopic)) {
            render "You are not authorized to create link for this topic"
        }

        String documentRoot = grailsApplication.config.grails.linksharing.files.rootPath
        String filePath = "${documentRoot}/${session?.loggedUser?.id}/${documentResourceCO.documentTopic?.id}"
        String fileNameWithPath = "${filePath}/${documentResourceCO.document.originalFilename}"

        if (documentResourceCO.hasErrors()) {
            render "Details had errors ${documentResourceCO.errors}"
        } else if (documentResourceService.create(documentResourceCO, session?.loggedUser, fileNameWithPath)) {
            new File(filePath).mkdirs()
            documentResourceCO.document.transferTo(new File(fileNameWithPath))
            render "Document Created"
        } else {
            render "Error occurred"
        }
    }

//    def saveDocument(DocumentResourceCO documentResourceCO){
//        User user=User.findByUsername((String)session["username"])
//        MultipartFile file=documentResourceCO.file
//        String path=request.getRealPath('/')
//        path=path+'/rohan/'+file.originalFilename
//// new File(path).createNewFile()
//        file.transferTo(new File(path))
//        documentResourceService.saveDocumentResource(user,documentResourceCO,path)
//        redirect(controller: "home",action: "index")
//    }
}
