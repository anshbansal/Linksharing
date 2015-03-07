package com.ttd.linksharing.service.domain

import com.ttd.linksharing.vo.PostDetails
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

    List<PostDetails> getReadingItemsForUser(User user) {

        ReadingItem.unreadForUser(user).list(max: 5)
                .collect([]) { ReadingItem readingItem ->

            new PostDetails(resource: readingItem.resource, isRead: readingItem.isRead)
        }
    }
}
