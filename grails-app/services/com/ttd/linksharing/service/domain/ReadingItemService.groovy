package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import grails.transaction.Transactional

@Transactional
class ReadingItemService {

    ReadingItem save(ReadingItem readingItem, Boolean isFlushEnabled = false, Boolean failOnError = false) {
        if(readingItem.validate() && readingItem.save(flush: isFlushEnabled, failOnError: failOnError)) {

        } else {
            readingItem = null
        }
        readingItem
    }
}
