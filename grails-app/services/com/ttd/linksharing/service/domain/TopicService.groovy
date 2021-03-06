package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.topic.TopicInfo
import com.ttd.linksharing.co.topic.TopicSeriousnessDetails
import com.ttd.linksharing.co.topic.TopicVisibility
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

    def updateTopicSeriousness(TopicSeriousnessDetails topicSeriousnessDetails, User user) {
        Subscription subscription = Subscription.findByTopicAndUser(topicSeriousnessDetails.topic, user)
        subscription.seriousness = topicSeriousnessDetails.newSeriousness
        subscription.save()
    }

    def updateTopicVisibility(TopicVisibility topicVisibility) {
        topicVisibility.topic.scope = topicVisibility.newVisibility
        topicVisibility.topic.save()
    }

    PagedResult<TopicDetails> getTopicsDetailsForUserSubscriptions(User user, QueryParameters params) {

        PagedResultList subscriptionsForUser = Subscription.createCriteria().list(params.queryMapParams) {
            createAlias('topic', 't')

            eq 'user', user

            filterTopicToBeShownToUserForSearchTerm.delegate = delegate
            filterTopicToBeShownToUserForSearchTerm(params.loggedUser, 't', params.searchTerm)
        }

        getListOfTopicDetailsFromPagedResultList(subscriptionsForUser, TopicDetails.mapFromSubscriptions)
    }

    PagedResult<TopicDetails> getTopicsDetailsForTopicsCreatedByUser(User user, QueryParameters params) {

        PagedResultList topicsForUser = Topic.createCriteria().list(params.queryMapParams) {
            eq 'createdBy', user

            filterTopicToBeShownToUserForSearchTerm.delegate = delegate
            filterTopicToBeShownToUserForSearchTerm(params.loggedUser, null, params.searchTerm)
            order 'name'
        }

        getListOfTopicDetailsFromPagedResultList(topicsForUser, TopicDetails.mapFromTopics)
    }

    PagedResult<TopicDetails> getTopicDetailsForAllTopicsForAdmin(QueryParameters params) {
        PagedResultList topicsForUser = Topic.createCriteria().list(params.queryMapParams) {
            filterTopicToBeShownToUserForSearchTerm.delegate = delegate
            filterTopicToBeShownToUserForSearchTerm(params.loggedUser, null, params.searchTerm)
            order 'name'
        }
        getListOfTopicDetailsFromPagedResultList(topicsForUser, TopicDetails.mapFromTopics)
    }

    TopicDetails getTopicDetailsForTopic(Topic topic) {
        new TopicDetails(topic: topic, creator: topic.createdBy,
                numSubscriptions: Subscription.countByTopic(topic), numResources: Resource.countByTopic(topic))
    }

    PagedResult<TopicDetails> getTrendingTopics(QueryParameters params) {
        //TODO Eager fetching not working
        PagedResultList pagedResultList = Subscription.createCriteria().list(params.queryMapParams) {

            createAlias('topic', 't')
            projections {
                groupProperty('topic')
                rowCount('myCount')
            }

            fetchMode('topic', FetchMode.JOIN)
            fetchMode('t.createdBy', FetchMode.JOIN)
            order 'myCount', 'desc'
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
        getNumberOfPropertyMappedByTopicIds.delegate = Subscription
        getNumberOfPropertyMappedByTopicIds(topicIds)
    }

    def Map getNumberOfResourcesForTopicIds(List<Long> topicIds) {
        getNumberOfPropertyMappedByTopicIds.delegate = Resource
        getNumberOfPropertyMappedByTopicIds(topicIds)
    }

    private def getNumberOfPropertyMappedByTopicIds = {List<Long> topicIds ->
        List properties = createCriteria().list{
            createAlias('topic', 't')
            projections {
                groupProperty('t.id')
                rowCount()
            }
            'in' 't.id', topicIds
        }
        Mappings.getIdToPropertyMapping(properties)
    }

    static def filterTopicToBeShownToUserForSearchTerm = { User loggedUser, String topicAlias, String topicNameSearchTerm ->

        if (topicAlias != null) {
            topicAlias = topicAlias + "."
        } else {
            topicAlias = ""
        }

        def scopeAlias = "${topicAlias}scope"
        def idAlias = "${topicAlias}id"
        def nameAlias = "${topicAlias}name"

        //Check whether only public topics to be shown
        Boolean onlyPubic = true
        List topicIdsToBeShown = null
        if (!loggedUser?.admin) {
            topicIdsToBeShown = SubscriptionService.getSubscribedPrivateTopicIdsForUser(loggedUser)
            onlyPubic = false
        }

        if (onlyPubic) {
            eq scopeAlias, Visibility.PUBLIC
        } else {
            or {
                eq scopeAlias, Visibility.PUBLIC
                if (topicIdsToBeShown.size() > 0) {
                    'in' idAlias, topicIdsToBeShown
                }
            }
        }

        if (topicNameSearchTerm) {
            ilike nameAlias, '%' + topicNameSearchTerm + '%'
        }
    }
}
