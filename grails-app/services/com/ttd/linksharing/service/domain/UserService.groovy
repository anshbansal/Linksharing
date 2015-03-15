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
import org.apache.commons.lang.RandomStringUtils

@Transactional
class UserService {

    def sendMailService

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
        user.active ? user : null
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
        String newPassword = RandomStringUtils.random(20, true, true)

        user.password = newPassword
        save(user)

        return newPassword
    }

    UserDetails getUserDetailsForUser(User user, Boolean includePrivates) {
        UserDetails userDetails = new UserDetails(user: user)

        updateSubscriptionAndTopicsCountInUsersDetail([userDetails], includePrivates)[0]
    }

    PagedResult<UserDetails> getUsersSubscribedToTopic(Topic topic, QueryParameters params) {
        List<PagedResultList> pagedResultList = Subscription.forTopic(topic).list(params.queryMapParams)

        PagedResult<UserDetails> userDetailsPagedResult = new PagedResult<UserDetails>()
                .setPaginationList(pagedResultList, UserDetails.mapFromSubscriptions)

        userDetailsPagedResult.paginationList = updateSubscriptionAndTopicsCountInUsersDetail(
                userDetailsPagedResult.paginationList, params.includePrivates)

        userDetailsPagedResult
    }

    List<UserDetails> updateSubscriptionAndTopicsCountInUsersDetail(List<UserDetails> userDetailsList,
                                                                    Boolean includePrivates) {

        Map temp = getNumberSubscriptionsAndTopics(userDetailsList*.userId, includePrivates)

        userDetailsList.collect([]) { UserDetails userDetails ->
            Map userDetailsMap = temp[userDetails.userId] as Map

            new UserDetails(user: userDetails.user, numSubscriptions: userDetailsMap['numSubs'], numTopics: userDetailsMap['numTopics'])
        }
    }

    //TODO Refactor
    private Map getNumberSubscriptionsAndTopics(List<Long> userIds, Boolean includePrivates) {
        Map result = [:]

        User.executeQuery("""
            select u.id,
                (select count(*) from Subscription
                    where user.id = u.id
                      and topic.scope in (:scopes)
                    ) as numSubs,
                (select count(*) from Topic
                    where createdBy.id = u.id
                      and scope in (:scopes)
                    ) as numTopics
            from User as u
            where u.id in (:ids)
            """, ['ids': userIds,'scopes':  Mappings.getScopes(includePrivates)])
                .each {
                    result[it[0]] = [numSubs: it[1], numTopics: it[2]]
                }

        return result
    }
}
