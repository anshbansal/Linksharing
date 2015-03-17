package com.ttd.linksharing.service.domain

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.enums.Visibility
import com.ttd.linksharing.util.Mappings
import com.ttd.linksharing.vo.QueryParameters
import grails.gorm.PagedResultList
import grails.transaction.NotTransactional
import grails.transaction.Transactional


@Transactional
class SubscriptionService {

    def readingItemService

    Subscription save(Subscription subscription, Boolean isFlushEnabled = false) {

        if (! subscription.save(flush: isFlushEnabled)) {
            return null
        }

        Resource.findAllWhere(topic: subscription.topic).each { Resource resource ->
            ReadingItem readingItem = new ReadingItem(resource: resource, user: subscription.user)
            readingItemService.save(readingItem, isFlushEnabled)
        }
        subscription
    }

    @NotTransactional
    List<Long> getSubscribedPrivateTopicIdsForUser(User user) {
        Subscription.createCriteria().list {

            createAlias('topic', 't')

            projections {
                property('t.id')
            }

            eq 't.scope', Visibility.PRIVATE
            eq 'user', user
        }
    }
}
