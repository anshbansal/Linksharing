package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.ReadingItemCO
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

    List<ReadingItemCO> getReadingItemsForUser(User user) {

        List<ReadingItemCO> readingItemCOList = []

        ReadingItem.unreadForUser(user).list(max: 5).each { ReadingItem readingItem ->

            readingItemCOList << new ReadingItemCO(resource: readingItem.resource, isRead: readingItem.isRead)
        }
        return readingItemCOList
    }
}
