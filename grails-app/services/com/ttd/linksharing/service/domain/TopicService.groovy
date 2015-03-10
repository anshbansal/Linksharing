package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.TopicDetails
import grails.gorm.PagedResultList
import grails.transaction.Transactional

@Transactional
class TopicService {

    def subscriptionService

    Topic create(TopicInfo info, User user) {
        if (isTopicPresentForUser(user, info.topicName)) {
            return null
        }
        save(new Topic(info, user))
    }

    def save(Topic topic, Boolean isFlushEnabled = false) {

        if (! topic.save(flush: isFlushEnabled)) {
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)

        topic
    }

    PagedResult<TopicDetails> getSubscriptionsForUser(User user, Integer max, Integer offset) {
        List<PagedResultList> pagedResultList = Subscription.subscribedTopics(user).list(max: max, offset: offset)

        PagedResult<TopicDetails> topicsDetail = new PagedResult<TopicDetails>().setPaginationList(
                pagedResultList,
                {
                    it.collect([]) { Subscription subscription ->
                        new TopicDetails(topic: subscription.topic, creator: subscription.topic.createdBy)
                    }
                }
        )
        if (pagedResultList.size() > 0) {
            addNumberSubscriptionsAndResources(topicsDetail)
        }
        topicsDetail
    }

    Boolean isTopicPresentForUser(User user, String topicName) {
        Topic.createCriteria().count {
            eq 'createdBy', user
            eq 'name', topicName
        } > 0
    }

    PagedResult<TopicDetails> addNumberSubscriptionsAndResources(PagedResult<TopicDetails> topicsDetail) {

        List<TopicDetails> topicDetailsList = topicsDetail.paginationList

        List<Long> topicIds = []
        topicDetailsList.each {
            topicIds << it.topic.id.toLong()
        }

        Map temp = getNumberSubscriptionsAndResources(topicIds)

        topicDetailsList.each { TopicDetails topicDetails ->
            int topicId = topicDetails.topic.id.intValue()

            topicDetails.numResources = temp[topicId]['numRes']
            topicDetails.numSubscriptions = temp[topicId]['numSubs']
        }
        return topicsDetail
    }

    Map getNumberSubscriptionsAndResources(List<Long> topicIds) {
        Map result = [:]

        Topic.executeQuery("""
            select t.id,
                (select count(*) from Subscription where topic.id = t.id) as numSubs,
                (select count(*) from Resource where topic.id = t.id) as numRes
            from Topic as t
            where t.id in (:ids)
            """, ['ids': topicIds ])
                .each {
                    result[it[0].intValue()] = [numSubs: it[1].intValue() ?: 0, numRes: it[2].intValue() ?: 0]
                }

        return result
    }
}
