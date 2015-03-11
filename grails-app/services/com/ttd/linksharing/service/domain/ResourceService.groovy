package com.ttd.linksharing.service.domain

import com.ttd.linksharing.util.Mappings
import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User
import grails.gorm.PagedResultList
import grails.transaction.Transactional
import org.hibernate.FetchMode

@Transactional
class ResourceService {

    def readingItemService

    def save(Resource resource, Boolean isFlushEnabled = false) {

        if (! resource.save(flush: isFlushEnabled)) {
            return null
        }

        List<Subscription> resourceSubscriptions = Subscription.findAllWhere(topic: resource.topic)

        resourceSubscriptions*.user.each { User subscribedUser ->
            ReadingItem readingItem = new ReadingItem(resource: resource, user: subscribedUser)
            readingItemService.save(readingItem, isFlushEnabled)
        }
        resource
    }

    PagedResult<PostDetails> recentPublicResources(Integer max, Integer offset) {

        List<PagedResultList> pagedResultList =
                Resource.recentPublicResources.list(max: max, offset: offset)

        new PagedResult<PostDetails>().setPaginationList(
                pagedResultList,
                {
                    it.collect([]) { Resource resource ->
                        new PostDetails(resource: resource)
                    }
                }
        )
    }

    PagedResult<PostDetails> getPostsForUser(User user, Boolean includePrivates, Integer max, Integer offset) {
        List<PagedResultList> pagedResultList = Resource.createCriteria().list(max: max, offset: offset) {

            createAlias('topic', 't')

            eq 'createdBy', user
            inList 't.scope', Mappings.getScopes(includePrivates)

            fetchMode('topic', FetchMode.JOIN)
            fetchMode('createdBy', FetchMode.JOIN)
        }


        new PagedResult<PostDetails>().setPaginationList(
                pagedResultList,
                {
                    it.collect([]) { Resource resource ->
                        new PostDetails(resource: resource)
                    }
                }
        )
    }
}
