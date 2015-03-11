package com.ttd.linksharing.service.domain

import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.vo.QueryParameters
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

    PagedResult<PostDetails> getReadingItemsForUser(User user, QueryParameters params) {

        def criteria = ReadingItem.unreadForUser(user)
        if (params.searchTerm != "") {
            criteria = criteria.resourceDescriptionLike(params.searchTerm)
        }

        List<PagedResultList> pagedResultList = criteria.list(max: params.max, offset: params.offset)

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
