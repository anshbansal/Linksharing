package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.Subscription
import com.ttd.linksharing.domain.User

class ApplicationTagLib {
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def resourceService
    def subscriptionService
    def topicService
    def userService

    def listings = { attrs ->
        out << render(
                template: "/templates/listings",
                model: [
                        renderTemplate: attrs.template,
                        title         : attrs.title,
                        listings      : attrs.listings
                ]
        )
    }

    /**
     * Creates a Post's container as per predefined post types
     *
     * @attr title The title of the Posts' container
     * @attr postsType REQUIRED The type of posts to be shown
     * @attr user The user for which posts to be shown
     */
    def posts = { attrs ->

        User loggedUser = attrs.loggedUser
        def title = ""

        switch (attrs.postsType) {
            case "recentShares":
                title = "Recent Shares"
                attrs.listings = Resource.recentPublicResources.list(max: 5)
                break
            case "topPosts":
                title = "Top Posts"
                //TODO Add logic for Top Posts
                attrs.listings = Resource.recentPublicResources.list(max: 5)
                break
            case "inbox":
                title = "Inbox"
                attrs.listings = ReadingItem.unreadForUser(loggedUser).list(max: 5)
                break
        }

        attrs.template = "/templates/post/post"
        attrs.title = attrs.title ?: title
        out << listings(attrs)
    }

    /**
     * Creates a Post's container as per predefined post types
     *
     * @attr title The title of the Posts' container
     * @attr topicsType REQUIRED The type of topics to be shown
     */
    def topics = { attrs ->
        User loggedUser = attrs.loggedUser
        def title = ""

        switch (attrs.topicsType) {
            case "subscriptions":
                title = "Subscriptions"
                attrs.listings = Subscription.subscribedTopics(loggedUser).list(max: 5)
                break
        }

        attrs.template = "/templates/topic/topic"
        attrs.title = attrs.title ?: title
        out << listings(attrs)
    }

    /**
     * Allows to mark a post as (Un)Read for logged in user
     *
     * @attr post REQUIRED The post
     */
    def markRead = { attrs ->
        Resource resource = attrs.post
        User loggedUser = attrs.loggedUser

        out << render(
                template: "/templates/post/markRead",
                model: [isRead: ReadingItem.isReadForUser(resource, loggedUser).get()]
        )
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
}
