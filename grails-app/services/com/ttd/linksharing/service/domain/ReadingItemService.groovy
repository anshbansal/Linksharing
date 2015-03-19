package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.Resource
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
            println "Errors occured ${readingItem.errors}"
            return null
        }
        readingItem
    }

    @NotTransactional
    PagedResult<PostDetails> getReadingItemsForUser(User user, QueryParameters params) {

        PagedResultList pagedResultList = ReadingItem.createCriteria().list(params.queryMapParams) {
            eq ('user', user)
            eq('isRead', Boolean.FALSE)

            if (params.searchTerm) {
                'resource' {
                    ilike 'description', '%' + params.searchTerm + '%'
                }
            }
        }

        PagedResult<PostDetails> readingItems = new PagedResult<PostDetails>()
        readingItems.paginationList = PostDetails.mapFromReadingItem(pagedResultList)

        if (pagedResultList.size() > 0) {
            readingItems.totalCount = pagedResultList.totalCount
        }

        readingItems
    }
    
    ReadingItem toggleResourceReadStatus(User user, Long resourceId, Boolean isRead) {
        Resource resource = Resource.get(resourceId)
        ReadingItem readingItem = ReadingItem.findByUserAndResource(user, resource)

        readingItem.isRead = ! isRead
        save(readingItem)
    }
}
