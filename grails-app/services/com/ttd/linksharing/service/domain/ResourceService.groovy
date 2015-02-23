package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class ResourceService {

    def readingItemService

    Resource save(Resource resource, Boolean isFlushEnabled = false, Boolean failOnError = false) {
        if(resource.validate() && resource.save(flush: isFlushEnabled, failOnError: failOnError)) {
            resource.subscribedUsers.each { User subscribedUser ->
                ReadingItem readingItem = new ReadingItem(resource: resource, user: subscribedUser)
                readingItemService.save(readingItem, isFlushEnabled, failOnError)
            }
        } else {
            resource = null
        }
        resource
    }
}
