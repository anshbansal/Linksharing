package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.User
import grails.transaction.Transactional

@Transactional
class ReadingItemService {

    def save(ReadingItem readingItem, Boolean isFlushEnabled = false) {

        if (! readingItem.save(flush: isFlushEnabled)) {
            return null
        }
        readingItem
    }

    List<ReadingItem> findForUser(User user) {
        ReadingItem.findAllWhere(user: user)
    }
}
