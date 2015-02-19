package com.ttd.linksharing.domain

class ResourceRating {

    Resource resource
    User user
    Integer score

    static belongsTo = [resource: Resource, user : User]
}
