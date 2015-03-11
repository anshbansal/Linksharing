package com.ttd.linksharing.service.domain

import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.User
import grails.gorm.PagedResultList
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

    PagedResult<PostDetails> getReadingItemsForUser(User user, Integer max, Integer offset, String searchTerm = "") {


        def criteria = ReadingItem.unreadForUser(user)
        if (searchTerm != "") {
            criteria = criteria.resourceDescriptionLike(searchTerm)
        }

        List<PagedResultList> pagedResultList = criteria.list(max: max, offset: offset)

        new PagedResult<PostDetails>().setPaginationList(
                pagedResultList,
                {
                    it.collect([]) { ReadingItem readingItem ->
                        new PostDetails(resource: readingItem.resource, isRead: readingItem.isRead)
                    }
                }
        )
    }
}
