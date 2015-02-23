package com.ttd.linksharing.domain

class DocumentResource extends Resource {

    String filePath

    static constraints = {
        filePath blank: false
    }
}
