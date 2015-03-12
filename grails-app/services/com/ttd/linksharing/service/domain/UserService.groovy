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

    User updateDetails(UserDetailsCO userDetailsCO, User user) {

        if (!isUniqueIdentifierValid(userDetailsCO.email, user)) {
            return null
        }

        user.firstName = userDetailsCO.firstName
        user.lastName = userDetailsCO.lastName
        user.email = userDetailsCO.email
        user.photo = userDetailsCO.photo
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

    PagedResult<UserDetails> getUsersSubscribedToTopic(Topic topic, QueryParameters params) {
        List<PagedResultList> pagedResultList = Subscription.forTopic(topic).list(max: params.max, offset: params.offset)

        PagedResult<UserDetails> userDetailsPagedResult = new PagedResult<UserDetails>()
                .setPaginationList(pagedResultList, UserDetails.mapFromSubscriptions)

        userDetailsPagedResult.paginationList = updateSubscriptionAndTopicsCountInUsersDetail(
                userDetailsPagedResult.paginationList, params.includePrivates)

        userDetailsPagedResult
    }

    List<UserDetails> updateSubscriptionAndTopicsCountInUsersDetail(List<UserDetails> userDetailsList,
                                                                    Boolean includePrivates) {
        Map temp = getNumberSubscriptionsAndTopics(userIdsFrom(userDetailsList)*.toLong())

        userDetailsList.collect([]) { UserDetails userDetails ->
            int userId = userIdFrom(userDetails)
            int numSubscriptions = temp[userId]['numSubs']
            int numTopics = temp[userId]['numTopics']

            new UserDetails(user: userDetails.user, numSubscriptions: numSubscriptions, numTopics: numTopics)
        }
    }

    Map getNumberSubscriptionsAndTopics(List<Long> userIds, Boolean includePrivates = Boolean.FALSE) {
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
                    result[it[0].intValue()] = [numSubs: it[1].intValue() ?: 0, numTopics: it[2].intValue() ?: 0]
                }

        return result
    }

    @NotTransactional
    private static List<Integer> userIdsFrom(List<UserDetails> userDetailsList) {
        userDetailsList.collect([]) { UserDetails userDetails ->
            userIdFrom(userDetails)
        }
    }

    @NotTransactional
    private static Integer userIdFrom(UserDetails userDetails) {
        userDetails.user.id
    }
}
