package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
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

        if (! topic.save(flush: isFlushEnabled)) {
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)

        topic
    }

    PagedResult<TopicDetails> getSubscriptionsForUser(User user, QueryParameters params) {
        def criteria = subscriptionService.getFilteredCriteriaForSubscription(Subscription.forUser(user), params)
        getTopicDetailsFromCriteria(criteria, params, TopicDetails.mapFromSubscriptions)
    }

    PagedResult<TopicDetails> getTopicsForUser(User user, QueryParameters params) {
        def criteria = getFilteredCriteriaForTopic(Topic.forUser(user), params)
        getTopicDetailsFromCriteria(criteria, params, TopicDetails.mapFromTopics)
    }

    TopicDetails getTopicDetailsForTopic(Topic topic) {
        new TopicDetails(topic: topic, creator: topic.createdBy,
               numSubscriptions: Subscription.countByTopic(topic), numResources: Resource.countByTopic(topic))
    }

    PagedResult<TopicDetails> getTrendingTopics(QueryParameters params) {
        //TODO Needs review
        //TODO Eager fetching not working
        List<PagedResultList> pagedResultList = Resource.createCriteria().list(params.queryMapParams) {

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

        topicsDetail.paginationList = pagedResultList.collect([]){
            new TopicDetails(topic: it[0], creator: it[0].createdBy)
        }

        //Pattern not followed here bcause totalCount was giving count of resources instead of count of group property.
//        topicsDetail.totalCount = Topic.where {
//            resources.size() > 0
//        }.count()

        getUpdatedTopicsDetail(topicsDetail)
    }

    Boolean isTopicPresentForUser(User user, String topicName) {
        Topic.createCriteria().count {
            eq 'createdBy', user
            eq 'name', topicName
        } > 0
    }

    private PagedResult<TopicDetails> getTopicDetailsFromCriteria(def criteria, QueryParameters params, Closure collector) {
        List<PagedResultList> pagedResultList = criteria.list(params.queryMapParams)

        PagedResult<TopicDetails> topicsDetail = new PagedResult<>()
        topicsDetail.setPaginationList(pagedResultList, collector)

        getUpdatedTopicsDetail(topicsDetail)
    }

    private PagedResult<TopicDetails> getUpdatedTopicsDetail(PagedResult<TopicDetails> topicsDetail) {
        topicsDetail?.with {
            if (size() > 0) {
                paginationList = getTopicsDetailWithSubscriptionAndResourceCount(paginationList)
            }
        }
        topicsDetail
    }

    private List<TopicDetails> getTopicsDetailWithSubscriptionAndResourceCount(List<TopicDetails> topicDetailsList) {

        Map temp = getNumberSubscriptionsAndResources(topicDetailsList*.topicId)

        topicDetailsList.each { TopicDetails topicDetails ->

            Map topicDetailsMap = temp[topicDetails.topicId] as Map

            new TopicDetails(topic: topicDetails.topic, creator: topicDetails.creator,
                    numSubscriptions: topicDetailsMap['numSubs'], numResources: topicDetailsMap['numRes'])
        }
    }

    //TODO Refactor for individual maps
    private Map getNumberSubscriptionsAndResources(List<Long> topicIds) {
        Map result = [:]

        Topic.executeQuery("""
            select t.id,
                (select count(*) from Subscription where topic.id = t.id) as numSubs,
                (select count(*) from Resource where topic.id = t.id) as numRes
            from Topic as t
            where t.id in (:ids)
            """, ['ids': topicIds ])
                .each {
                    result[it[0]] = [numSubs: it[1], numRes: it[2]]
                }

        return result
    }

    static def getFilteredCriteriaForTopic(def criteria, QueryParameters params) {
        if (params.loggedUser) {
            if (! params.loggedUser.admin) {
                criteria = criteria.showTopicToUser(params.loggedUser)
            }
        } else {
            criteria = criteria.publicTopics
        }
        if (params.searchTerm) {
            criteria = criteria.nameLike(params.searchTerm)
        }
        return criteria
    }
}
