package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.enums.Visibility
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

        if (!topic.save(flush: isFlushEnabled)) {
            println "Topic had errors ${topic.errors}"
            return null
        }

        Subscription subscription = new Subscription(user: topic?.createdBy, topic: topic)
        subscriptionService.save(subscription, isFlushEnabled)

        topic
    }

    def deleteTopicById(Long topicId) {
        Topic topic = Topic.get(topicId)
        topic.delete()
    }

    PagedResult<TopicDetails> getTopicsDetailsForUserSubscriptions(User user, QueryParameters params) {

        PagedResultList subscriptionsForUser = Subscription.createCriteria().list(params.queryMapParams) {
            createAlias('topic', 't')

            eq 'user', user

            List<Long> topicIdsToBeShown = getTopicIdsToBeShownToUser(params.loggedUser)
            if (topicIdsToBeShown?.size()) {
                'in' 't.id', topicIdsToBeShown
            }
        }

        getListOfTopicDetailsFromPagedResultList(subscriptionsForUser, TopicDetails.mapFromSubscriptions)
    }

    PagedResult<TopicDetails> getTopicsDetailsForTopicsCreatedByUser(User user, QueryParameters params) {

        PagedResultList topicsForUser = Topic.createCriteria().list(params.queryMapParams) {
            eq 'createdBy', user

            List<Long> topicIdsToBeShown = getTopicIdsToBeShownToUser(params.loggedUser, params.searchTerm)
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

    PagedResult<TopicDetails> getListOfTopicDetailsFromPagedResultList(PagedResultList listResults, Closure collector) {

        List<TopicDetails> topicDetailsList = collector(listResults)

        if (listResults?.size() > 0) {
            topicDetailsList = getTopicsDetailWithSubscriptionAndResourceCount(topicDetailsList)
        }

        new PagedResult<>(paginationList: topicDetailsList, totalCount: listResults.totalCount)
    }

    List<TopicDetails> getTopicsDetailWithSubscriptionAndResourceCount(List<TopicDetails> topicDetailsList) {

        Map numSubscriptionsForTopics = getNumberOfSubscriptionsForTopicIds(topicDetailsList*.topicId)
        Map numResourcesForTopics = getNumberOfResourcesForTopicIds(topicDetailsList*.topicId)

        List<TopicDetails> result = []
        topicDetailsList.collect(result) { TopicDetails topicDetails ->

            Topic curTopic = topicDetails.topic

            new TopicDetails(topic: curTopic, creator: topicDetails.creator,
                    numSubscriptions: numSubscriptionsForTopics[curTopic.id],
                    numResources: numResourcesForTopics[curTopic.id])
        }
        result
    }

    Topic updateTopicNameById(String newTopicName, Long topicId) {
        Topic topic = Topic.findById(topicId)
        topic.name = newTopicName
        topic.save()
    }

    def Map getNumberOfSubscriptionsForTopicIds(List<Long> topicIds) {
        def subscriptions = Subscription.createCriteria().list {
            getNumberOfPropertyMappedByTopicIds.delegate = delegate
            getNumberOfPropertyMappedByTopicIds(topicIds)
        }
        Mappings.getIdToPropertyMapping(subscriptions)
    }

    def Map getNumberOfResourcesForTopicIds(List<Long> topicIds) {
        def resources = Resource.createCriteria().list {
            getNumberOfPropertyMappedByTopicIds.delegate = delegate
            getNumberOfPropertyMappedByTopicIds(topicIds)
        }
        Mappings.getIdToPropertyMapping(resources)
    }

    private def getNumberOfPropertyMappedByTopicIds = {List<Long> topicIds ->
        createAlias('topic', 't')
        projections {
            groupProperty('t.id')
            rowCount()
        }
        'in' 't.id', topicIds
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
