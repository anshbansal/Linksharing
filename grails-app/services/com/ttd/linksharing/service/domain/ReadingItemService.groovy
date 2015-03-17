package com.ttd.linksharing.service.domain

import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.vo.QueryParameters
import grails.gorm.PagedResultList
import grails.transaction.NotTransactional
import grails.transaction.Transactional

@Transactional
class ReadingItemService {

    def save(ReadingItem readingItem, Boolean isFlushEnabled = false) {

        if (! readingItem.save(flush: isFlushEnabled)) {
            return null
        }
        readingItem
    }

    @NotTransactional
    PagedResult<PostDetails> getReadingItemsForUser(User user, QueryParameters params) {

        def criteria = ReadingItem.readingItemForUser(user).unreadItems()
        if (params.searchTerm != "") {
            criteria = criteria.resourceDescriptionLike(params.searchTerm)
        }

        PagedResultList pagedResultList = criteria.list(max: params.max, offset: params.offset)

        PagedResult<PostDetails> readingItems = new PagedResult<PostDetails>()
        readingItems.paginationList = PostDetails.mapFromReadingItem(pagedResultList)

        if (pagedResultList.size() > 0) {
            readingItems.totalCount = pagedResultList.totalCount
        }

        readingItems
    }
}
