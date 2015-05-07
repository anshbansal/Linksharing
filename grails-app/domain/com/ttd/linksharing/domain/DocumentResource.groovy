package com.ttd.linksharing.domain

class DocumentResource extends Resource {

    String filePath
    String fileType

    static constraints = {
        filePath blank: false
    }
}
