package com.ttd.linksharing.service.domain

import com.ttd.linksharing.co.user.LoginCredentials
import com.ttd.linksharing.co.user.RegistrationCO
import com.ttd.linksharing.co.user.UserDetailsCO
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.util.Mappings
import com.ttd.linksharing.vo.PagedResult
import com.ttd.linksharing.vo.QueryParameters
import com.ttd.linksharing.vo.UserDetails
import grails.gorm.PagedResultList
import grails.transaction.NotTransactional
import grails.transaction.Transactional

@Transactional
class UserService {

    def sendMailService
    def topicService

    User save(User user, Boolean isFlushEnabled = false) {

        if (!user.save(flush: isFlushEnabled)) {
            log.debug("Error occurred during saving user ${user.errors}")
            return null
        }
        user
    }

    User registerUser(RegistrationCO registrationCO) {
        if (!isUniqueIdentifierValid(registrationCO.username) && isUniqueIdentifierValid(registrationCO.email)) {
            return null
        }
        save(registrationCO.user)
    }

    @NotTransactional
    User getActiveUser(LoginCredentials credentials) {
        User user = User.withCredentials(credentials).get()
        user?.active ? user : null
    }

    Boolean updatePassword(User user, String newPassword) {
        user.setPassword(newPassword)
        save(user)
        user.hasErrors()
    }

    Boolean isUniqueIdentifierValid(String newIdentifier, User user = null) {

        def criteria = {}
        if (user) {
            criteria = { not { idEq(user.id) } }
        }

        User.byIdentifier(newIdentifier).count(criteria) == 0
    }

    Boolean isEmailPresent(String email) {
        User.countByEmail(email) == 1
    }

    User updateDetails(UserDetailsCO userDetailsCO, Long userId) {

        User user = User.get(userId)

        if (!isUniqueIdentifierValid(userDetailsCO.email, user)) {
            return null
        }

        user.firstName = userDetailsCO.firstName
        user.lastName = userDetailsCO.lastName
        user.email = userDetailsCO.email
        user.photo = userDetailsCO.photo
        user.avatarType = userDetailsCO.avatarType
        save(user)
    }

    def resetPasswordAndSendMail(String uniqueIdentifier) {
        User user = User.byIdentifier(uniqueIdentifier).get()
        String newPassword = resetPassword(user)

        sendMailService.sendResetMail(user, newPassword)
    }

    String resetPassword(User user) {
        String newPassword = Mappings.getRandomStringOfSize(20)

        user.password = newPassword
        save(user)

        return newPassword
    }

    UserDetails getUserDetailsForUser(User user, User loggedUser) {
        UserDetails userDetails = new UserDetails(user: user)

        getUserDetailsWithSubscriptionAndTopicCount([userDetails], loggedUser)[0]
    }

    PagedResult<UserDetails> getUsersSubscribedToTopic(Topic topic, QueryParameters params) {
        PagedResultList pagedResultList = Subscription.createCriteria().list(params.queryMapParams) {
            eq 'topic', topic
        }

        PagedResult<UserDetails> userDetailsPagedResult = new PagedResult<>()

        userDetailsPagedResult.paginationList = UserDetails.mapFromSubscriptions(pagedResultList)
        userDetailsPagedResult.totalCount = pagedResultList.totalCount

        userDetailsPagedResult.paginationList = getUserDetailsWithSubscriptionAndTopicCount(userDetailsPagedResult.paginationList, params.loggedUser)

        userDetailsPagedResult
    }

    private List<UserDetails> getUserDetailsWithSubscriptionAndTopicCount(List<UserDetails> userDetailsList,
                                                                          User loggedUser) {

        List<Long> topicIdsToBeShown = topicService.getTopicIdsToBeShownToUser(loggedUser)

        Map numSubscriptionsForUser = getNumberOfSubscriptionsForUserIds(userDetailsList*.userId, topicIdsToBeShown)
        Map numTopicsForUser = getNumberOfTopicsForUserIds(userDetailsList*.userId, topicIdsToBeShown)

        userDetailsList.collect([]) { UserDetails userDetails ->
            new UserDetails(user: userDetails.user,
                    numSubscriptions: numSubscriptionsForUser[userDetails.userId],
                    numTopics: numTopicsForUser[userDetails.userId])
        }
    }

    def Map getNumberOfSubscriptionsForUserIds(List<Long> userIds, List<Long> topicIds) {
        def subscriptions = Subscription.createCriteria().list {
            createAlias 'user', 'u'
            createAlias 'topic', 't'
            projections {
                groupProperty('u.id')
                rowCount()
            }
            'in' 'u.id', userIds
            'in' 't.id', topicIds
        }
        Mappings.getIdToPropertyMapping(subscriptions)
    }

    def Map getNumberOfTopicsForUserIds(List<Long> userIds, List<Long> topicIds) {
        def topics = Topic.createCriteria().list {
            createAlias 'createdBy', 'u'
            projections {
                groupProperty('u.id')
                rowCount()
            }
            'in' 'u.id', userIds
            'in' 'id', topicIds
        }
        Mappings.getIdToPropertyMapping(topics)
    }
}
