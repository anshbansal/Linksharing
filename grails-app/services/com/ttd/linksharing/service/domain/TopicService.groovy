package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.enums.Visibility
import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.QueryParameters
import com.ttd.linksharing.vo.TopicDetails
import grails.gorm.PagedResultList
import grails.transaction.NotTransactional
import grails.transaction.Transactional
import org.hibernate.FetchMode

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

        if (!topic.save(flush: isFlushEnabled)) {
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)

        topic
    }

    PagedResult<TopicDetails> getTopicsDetailsForUserSubscriptions(User user, QueryParameters params) {
        List<Long> topicIdsToBeShown = getTopicIdsToBeShownToUser(params.loggedUser, params.searchTerm)

        PagedResultList subscriptionsForUser = Subscription.createCriteria().list(params.queryMapParams) {
            createAlias('topic', 't')

            eq 'user', user
            if (topicIdsToBeShown?.size()) {
                'in' 't.id', topicIdsToBeShown
            }
        }

        getListOfTopicDetailsFromPagedResultList(subscriptionsForUser, TopicDetails.mapFromSubscriptions)
    }

    PagedResult<TopicDetails> getTopicsDetailsForTopicsCreatedByUser(User user, QueryParameters params) {
        List<Long> topicIdsToBeShown = getTopicIdsToBeShownToUser(params.loggedUser, params.searchTerm)

        PagedResultList topicsForUser = Topic.createCriteria().list(params.queryMapParams) {
            eq 'createdBy', user
            if (topicIdsToBeShown?.size()) {
                'in' 'id', topicIdsToBeShown
            }
        }

        getListOfTopicDetailsFromPagedResultList(topicsForUser, TopicDetails.mapFromTopics)
    }

    TopicDetails getTopicDetailsForTopic(Topic topic) {
        new TopicDetails(topic: topic, creator: topic.createdBy,
                numSubscriptions: Subscription.countByTopic(topic), numResources: Resource.countByTopic(topic))
    }

    PagedResult<TopicDetails> getTrendingTopics(QueryParameters params) {
        //TODO Needs review
        //TODO Eager fetching not working
        PagedResultList pagedResultList = Resource.createCriteria().list(params.queryMapParams) {

            createAlias('topic', 't')

            projections {
                groupProperty('topic')
                count('id', 'resourceCount')
            }

            fetchMode('topic', FetchMode.JOIN)
            fetchMode('t.createdBy', FetchMode.JOIN)

            order('resourceCount', 'desc')
        }

        PagedResult<TopicDetails> topicsDetail = new PagedResult<>()
        topicsDetail.paginationList = pagedResultList.collect([]) {
            new TopicDetails(topic: it[0], creator: it[0].createdBy)
        }
        topicsDetail.paginationList = getTopicsDetailWithSubscriptionAndResourceCount(topicsDetail.paginationList)

        //Pattern not followed here bcause totalCount was giving count of resources instead of count of group property.
//        topicsDetail.totalCount = Topic.where {
//            resources.size() > 0
//        }.count()

        topicsDetail
    }

    Boolean isTopicPresentForUser(User user, String topicName) {
        Topic.createCriteria().count {
            eq 'createdBy', user
            eq 'name', topicName
        } > 0
    }

    PagedResult<TopicDetails> getListOfTopicDetailsFromPagedResultList(
            PagedResultList listResults, Closure<List<TopicDetails>> collector) {

        List<TopicDetails> topicDetailsList = collector(listResults)

        if (listResults?.size() > 0) {
            topicDetailsList = getTopicsDetailWithSubscriptionAndResourceCount(topicDetailsList)
        }

        new PagedResult<>(paginationList: topicDetailsList, totalCount: listResults.totalCount)
    }

    List<TopicDetails> getTopicsDetailWithSubscriptionAndResourceCount(List<TopicDetails> topicDetailsList) {

        Map temp = getNumberSubscriptionsAndResources(topicDetailsList*.topicId)

        topicDetailsList.each { TopicDetails topicDetails ->

            Map topicDetailsMap = temp[topicDetails.topicId] as Map

            new TopicDetails(topic: topicDetails.topic, creator: topicDetails.creator,
                    numSubscriptions: topicDetailsMap['numSubs'], numResources: topicDetailsMap['numRes'])
        }
    }

    //TODO Refactor for individual maps
    Map getNumberSubscriptionsAndResources(List<Long> topicIds) {
        Map result = [:]

        Topic.executeQuery("""
            select t.id,
                (select count(*) from Subscription where topic.id = t.id) as numSubs,
                (select count(*) from Resource where topic.id = t.id) as numRes
            from Topic as t
            where t.id in (:ids)
            """, ['ids': topicIds])
                .each {
            result[it[0]] = [numSubs: it[1], numRes: it[2]]
        }

        return result
    }

    @NotTransactional
    List<Long> getTopicIdsToBeShownToUser(User user, String topicNameSearchTerm = null) {
        if (user?.admin && !topicNameSearchTerm) {
            return null
        }

        Topic.createCriteria().list {
            projections {
                property('id')
            }

            if (user) {
                if (!user.admin) {
                    List<Long> subscribedPrivateTopicIdsForUser = subscriptionService
                                                                    .getSubscribedPrivateTopicIdsForUser(user)
                    or {
                        eq 'scope', Visibility.PUBLIC
                        if (subscribedPrivateTopicIdsForUser.size()) {
                            'in' 'id', subscribedPrivateTopicIdsForUser
                        }
                    }
                }
            } else {
                eq 'scope', Visibility.PUBLIC
            }

            if (topicNameSearchTerm) {
                ilike 'name', '%' + topicNameSearchTerm + '%'
            }
        }
    }
}
