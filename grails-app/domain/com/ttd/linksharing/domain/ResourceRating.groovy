package com.ttd.linksharing.domain

class ResourceRating {
    Integer score

    static belongsTo = [resource: Resource, user : User]
}
