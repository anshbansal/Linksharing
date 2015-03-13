package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.util.Mappings
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

    //TODO Subscription logic check
    PagedResult<TopicDetails> getSubscriptionsForUser(User user, QueryParameters params) {

        def criteria = Subscription.subscribedTopics(user)
        if (params.searchTerm) {
            criteria = criteria.topicNameLike(params.searchTerm)
        }

        getTopicDetailsFromCriteria(criteria, params, TopicDetails.mapFromSubscriptions)
    }

    PagedResult<TopicDetails> getTopicsForUser(User user, QueryParameters params) {

        def criteria = Topic.forUser(user)
        if (! params.includePrivates) {
            criteria = criteria.publicTopics()
        }
        if (params.searchTerm) {
            criteria = criteria.nameLike(params.searchTerm)
        }

        getTopicDetailsFromCriteria(criteria, params, TopicDetails.mapFromTopics)
    }

    @NotTransactional
    TopicDetails getTopicDetailsForTopic(Topic topic) {
        new TopicDetails(topic: topic, creator: topic.createdBy,
               numSubscriptions: Subscription.countByTopic(topic), numResources: Resource.countByTopic(topic))
    }

    private PagedResult<TopicDetails> getTopicDetailsFromCriteria(def criteria, QueryParameters params, Closure collector) {

        List<PagedResultList> pagedResultList = criteria.list(max: params.max, offset: params.offset)

        PagedResult<TopicDetails> topicsDetail = new PagedResult<>()
                .setPaginationList(criteria.list(max: params.max, offset: params.offset), collector)

        if (pagedResultList.size() > 0) {
            topicsDetail.paginationList = updateSubscriptionAndResourceCountInTopicsDetail(topicsDetail.paginationList)
        }
        topicsDetail
    }

    //TODO Needs review
    PagedResult<TopicDetails> getTrendingTopics(QueryParameters params) {
        List<PagedResultList> pagedResultList = Resource.createCriteria()
                .list(max: params.max, offset: params.offset) {

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

        if (pagedResultList.size() > 0) {
            topicsDetail.paginationList = updateSubscriptionAndResourceCountInTopicsDetail(topicsDetail.paginationList)
        }

        topicsDetail
    }

    Boolean isTopicPresentForUser(User user, String topicName) {
        Topic.createCriteria().count {
            eq 'createdBy', user
            eq 'name', topicName
        } > 0
    }

    List<TopicDetails> updateSubscriptionAndResourceCountInTopicsDetail(List<TopicDetails> topicDetailsList) {

        List<Long> topicIds = topicIdsFrom(topicDetailsList)

        Map temp = getNumberSubscriptionsAndResources(topicIds)

        topicDetailsList.each { TopicDetails topicDetails ->
            int topicId = topicIdFrom(topicDetails)

            Integer numSubscriptions = temp[topicId]['numRes']
            Integer numResources = temp[topicId]['numSubs']

            new TopicDetails(topic: topicDetails.topic, creator: topicDetails.creator,
                    numSubscriptions: numSubscriptions, numResources: numResources)
        }
    }

    Map getNumberSubscriptionsAndResources(List<Integer> topicIds) {
        Map result = [:]

        Topic.executeQuery("""
            select t.id,
                (select count(*) from Subscription where topic.id = t.id) as numSubs,
                (select count(*) from Resource where topic.id = t.id) as numRes
            from Topic as t
            where t.id in (:ids)
            """, ['ids': topicIds*.toLong() ])
                .each {
                    result[it[0].intValue()] = [numSubs: it[1].intValue(), numRes: it[2].intValue()]
                }

        return result
    }

    @NotTransactional
    private static List<Integer> topicIdsFrom(List<TopicDetails> topicDetailsList) {
        topicDetailsList.collect([]) { TopicDetails topicDetails ->
            topicIdFrom(topicDetails)
        }
    }

    @NotTransactional
    private static Integer topicIdFrom(TopicDetails topicDetails) {
        topicDetails.topic.id
    }
}
