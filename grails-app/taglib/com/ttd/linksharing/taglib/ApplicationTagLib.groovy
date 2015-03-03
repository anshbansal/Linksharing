package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.domain.User

class ApplicationTagLib {
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def resourceService
    def subscriptionService
    def topicService
    def userService
    def readingItemService

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
                attrs.listings = resourceService.recentPublicResources()
                break
            case "topPosts":
                title = "Top Posts"
                //TODO Add logic for Top Posts
                attrs.listings = resourceService.recentPublicResources()
                break
            case "inbox":
                title = "Inbox"
                attrs.listings = readingItemService.getReadingItemsForUser(loggedUser)
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
                attrs.listings = topicService.getSubscriptionsForUser(loggedUser)
                break
        }

        attrs.template = "/templates/topic/topic"
        attrs.title = attrs.title ?: title
        out << listings(attrs)
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
