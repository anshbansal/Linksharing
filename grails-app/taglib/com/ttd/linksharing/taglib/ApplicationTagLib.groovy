package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource

class ApplicationTagLib {
    static defaultEncodeAs = [taglib:'raw']
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
                        title: attrs.title,
                        listings: attrs.listings
                ]
        )
    }

    /**
     * Creates a Post's container as per predefined post types
     *
     * @attr title The title of the Posts' container
     * @attr postsType REQUIRED The type of posts to be shown
     */
    def posts = { attrs ->
        def title = ""

        switch (attrs.postsType) {
            case "recentShares":
                title = "Recent Shares"
                attrs.listings = Resource.recentPublicResources.list(max: 5)
                break
            case "inbox":
                title = "Inbox"
                attrs.listings = ReadingItem.unreadForUserName(session.user).list(max: 5)
                break
        }

        attrs.template = "/templates/commons/post/post"
        attrs.title = attrs.title ?: title
        out << listings(attrs)
    }

    def topics = { attrs ->
        def title = ""

        switch(attrs.topicsType) {
            case "subscriptions":
                title = "Subscriptions"
                attrs.listings = userService.getSubscribedTopicsForUser session.user
                break
        }

        attrs.template = "/templates/commons/topic/topic"
        attrs.title = attrs.title ?: title
        out << listings(attrs)
    }

    /**
     * Allows to mark a post as (Un)Read for logged in user
     *
     * @attr post REQUIRED The post
     */
    def markRead = { attrs ->

        if (! session.user) {
            return null
        }

        out << render(
                template: "/templates/commons/post/markRead",
                model: [isRead: resourceService.isRead(attrs.post, userService.findByUsername(session.user))]
        )

    }

    /**
     * @attr post REQUIRED The post
     */
    def resourceType = { attrs ->
        out << render(
                template: "/templates/commons/post/resourceType",
                model: [
                    post: attrs.post
                ]
        )
    }

    //TODO Test User image present
    def photo = { attrs ->
        out << render(template: "/templates/commons/photo",
                        model: [user: attrs.user]
        )
    }
}
