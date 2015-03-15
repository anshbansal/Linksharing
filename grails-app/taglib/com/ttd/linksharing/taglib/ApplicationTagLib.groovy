package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.Topic
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.vo.ListingDetails
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.vo.TopicDetails
import com.ttd.linksharing.vo.UserDetails
import grails.converters.JSON

class ApplicationTagLib {
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def resourceService
    def subscriptionService
    def topicService
    def userService
    def readingItemService

    /**
     * @attr title The title for the container
     * @attr paginationDisable Defaults(false). Set(true) if required
     * @attr searchEnable Defaults(false). Set(true) if required
     * @attr idToUpdate id for pagination/search results. Defaults to type
     * @attr type REQUIRED The type of posts to be shown.
     * @attr userId Default (not used). Considered if (type = forUser)
     * @attr topicId Default (not used). Considered if (type = forTopic)
     */
    def posts = { attrs ->
        ListingDetails<PostDetails> listingDetails = new ListingDetails<>(
                renderTemplate: "/templates/post/post", attrs: attrs, paginationController: "listingsPost")
        listingDetails.includePrivates = includePrivates(listingDetails.userId)

        //TODO Check logic of each for (isRead) after mark as read is implemented
        switch (attrs.type) {
            case "recentShares":
                listingDetails.title = "Recent Shares"
                listingDetails.listings = resourceService
                        .recentPublicResources(listingDetails.queryParams)
                break
//            case "topPosts":
//                title = "Top Posts"
//                //TODO Add logic for Top Posts
//                posts = resourceService.recentPublicResources()
//                break
            case "inbox":
                listingDetails.title = "Inbox"
                listingDetails.listings = readingItemService
                        .getReadingItemsForUser(session?.loggedUser, listingDetails.queryParams)
                break
            case "forUser":
                listingDetails.title = "Posts"
                listingDetails.listings = resourceService
                        .getPostsForUser(User.get(listingDetails.userId), listingDetails.queryParams)
                break
            case "forTopic":
                Topic curTopic = Topic.get(listingDetails.topicId)
                listingDetails.title = 'Posts: "' + curTopic?.name + '"'
                listingDetails.listings = resourceService.getPostsForTopic(curTopic, listingDetails.queryParams)
                break
        }

        out << render(listingDetails.renderMap)
    }

    /**
     * @attr title The title for the container
     * @attr paginationDisable Defaults(false). Set(true) if required
     * @attr searchEnable Defaults(false). Set(true) if required
     * @attr idToUpdate id for pagination/search results. Defaults to type
     * @attr type REQUIRED The type of topics to be shown.
     * @attr userId Default (not used). Used for (type forUser to show user topics)
 *                                               (type subscriptions to show user's subscriptions)
     */
    def topics = { attrs ->
        ListingDetails<TopicDetails> listingDetails = new ListingDetails<>(
                renderTemplate: "/templates/topic/topic", attrs: attrs, paginationController: 'listingsTopic')
        listingDetails.includePrivates = includePrivates(listingDetails.userId)

        switch (attrs.type) {
            case "subscriptions":
                listingDetails.title = "Subscriptions"
                User curUser

                //TODO Need to be changed for sort order
                if (listingDetails.userId != 0) {
                    curUser = User.get(listingDetails.userId)
                } else {
                    curUser = session?.loggedUser
                }
                listingDetails.listings = topicService.
                        getSubscriptionsForUser(curUser, listingDetails.queryParams)
                break
            case "trendingTopics":
                //Search and pagination is not available for this
                listingDetails.searchEnable = Boolean.FALSE
                listingDetails.paginationDisable = Boolean.TRUE
                listingDetails.title = "Trending Topics"
                listingDetails.listings = topicService.getTrendingTopics(listingDetails.queryParams)
                break
            case "forUser":
                listingDetails.title = "Topics"
                listingDetails.listings = topicService.
                        getTopicsForUser(User.get(listingDetails.userId), listingDetails.queryParams)
                break
        }

        out << render(listingDetails.renderMap)
    }

    def topic = { attrs ->
        out << render(template: "/templates/topic/topic",
                        model: [listing: topicService.getTopicDetailsForTopic(attrs.topic)])
    }

    /**
     * @attr title The title for the container
     * @attr paginationDisable Defaults(false). Set(true) if required
     * @attr idToUpdate id for pagination/search results. Defaults to type
     * @attr type REQUIRED The type of topics to be shown.
     * @attr topicId Default (not used). Considered if (type = forTopic)
     */
    def users = { attrs ->
        ListingDetails<UserDetails> listingDetails = new ListingDetails<>(
                renderTemplate: "/templates/user/user", attrs: attrs, paginationController: 'listingsUser')
        listingDetails.searchEnable = Boolean.FALSE
        listingDetails.includePrivates = includePrivates(listingDetails.userId)

        switch (attrs.type) {
            case "forTopic":
                Topic curTopic = Topic.get(listingDetails.topicId)
                listingDetails.title = 'Users: "' + curTopic.name + '"'
                listingDetails.listings = userService.getUsersSubscribedToTopic(curTopic, listingDetails.queryParams)
                break
        }
        out << render(listingDetails.renderMap)
    }

    /**
     * @attr user REQUIRED The user
     */
    def user = { attrs ->
        User user = attrs.user
        UserDetails details = userService.getUserDetailsForUser(user, includePrivates(user))

        out << render(template: "/templates/user/user", model: [listing: details])
    }

    /**
     * @attr post REQUIRED The post
     */
    def resourceType = { attrs ->
        out << render(template: "/templates/post/resourceType", model: [post: attrs.post])
    }

    def photo = { attrs ->
        out << render(template: "/templates/commons/photo",model: [user: attrs.user])
    }

    def flash = { attrs ->
        out << render(template: "/templates/commons/flash_message", model: [type: attrs.type])
    }

    /**
     * @attr searchText
     */
    def searchBox = { attrs ->
        out << render(template: "/templates/commons/search", model: [searchText: attrs.searchText])
    }

    def getTopicsForUser = { attrs ->
        out << g.select(name: attrs.name,
                from: Subscription.forUser(session?.loggedUser).list().topic,
                optionKey: 'id')

    }

    private Boolean includePrivates(User user) {
        includePrivates(user?.id)
    }

    private Boolean includePrivates(Long id) {
        session?.loggedUser?.id == id || session?.loggedUser?.admin
    }
}
