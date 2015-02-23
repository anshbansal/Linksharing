package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import grails.transaction.Transactional

import static com.ttd.linksharing.util.ServiceUtil.validateAndSave

@Transactional
class ReadingItemService {

    ReadingItem save(ReadingItem readingItem, Map args = [:]) {

        if (!validateAndSave(readingItem, args)) {
            return null
        }
        readingItem
    }
}
