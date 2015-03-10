package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.User
import com.ttd.linksharing.vo.ListingDetails
import com.ttd.linksharing.vo.PostDetails
import com.ttd.linksharing.vo.TopicDetails

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
     * @attr title The title of the Posts' container
     * @attr type REQUIRED The type of posts to be shown. Same as ID of the div being paginated
     */
    def posts = { attrs ->
        ListingDetails<PostDetails> listingDetails =
                new ListingDetails<>(renderTemplate: "/templates/post/post", attrs: attrs,
                        paginationController: "listingsPost")

        switch (attrs.type) {
            case "recentShares":
                listingDetails.title = "Recent Shares"
                listingDetails.listings = resourceService
                        .recentPublicResources(listingDetails.max, listingDetails.offset)
                break
//            case "topPosts":
//                title = "Top Posts"
//                //TODO Add logic for Top Posts
//                posts = resourceService.recentPublicResources()
//                break
            case "inbox":
                listingDetails.title = "Inbox"
                listingDetails.listings = readingItemService
                        .getReadingItemsForUser(session?.loggedUser, listingDetails.max, listingDetails.offset)
                break
        }

        out << render(listingDetails.renderMap)
    }

    /**
     * @attr title The title of the Posts' container
     * @attr type REQUIRED The type of topics to be shown
     */
    def topics = { attrs ->
        ListingDetails<TopicDetails> listingDetails =
                new ListingDetails<>(renderTemplate: "/templates/topic/topic", attrs: attrs,
                        paginationController: 'listingsTopic')

        switch (attrs.type) {
            case "subscriptions":
                listingDetails.title = "Subscriptions"
                listingDetails.listings = topicService.
                        getSubscriptionsForUser(session?.loggedUser, listingDetails.max, listingDetails.offset)
                break
        }

        out << render(listingDetails.renderMap)
    }

    /**
     * @attr post REQUIRED The post
     */
    def resourceType = { attrs ->
        Resource resource = attrs.post

        out << render(
                template: "/templates/post/resourceType",
                model: [post: resource]
        )
    }

    //TODO Test User image present
    def photo = { attrs ->
        User user = attrs.user

        out << render(
                template: "/templates/commons/photo",
                model: [user: user]
        )
    }

    def user = { attrs ->
        User user = attrs.user

        out << render(
                template: "/templates/user/user",
                model: [listing: user]
        )
    }

    def flash = { attrs ->
        out << render(template: "/templates/commons/flash_message", model: [type: attrs.type])
    }
}
