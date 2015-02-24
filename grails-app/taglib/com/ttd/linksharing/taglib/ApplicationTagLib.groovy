package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.User
import com.ttd.linksharing.util.ListUtil

class ApplicationTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    def userService
    def resourceService

    /**
     * Creates a Generic Listings' container.
     *
     * @attr title REQUIRED The title of the Posts' container
     * @attr posts REQUIRED The posts to be shown
     * @attr template REQUIRED The template of the listing to be rendered
     */
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
     * Creates a Post's container.
     *
     * @attr title REQUIRED The title of the Posts' container
     * @attr posts REQUIRED The posts to be shown
     */
    def posts = { attrs ->
        attrs.template = "/templates/commons/post/post"
        attrs.listings = attrs.posts

        out << listings(attrs)
    }

    /**
     * Creates a Post's container as per predefined post types
     *
     * @attr title The title of the Posts' container
     * @attr postsType REQUIRED The type of posts to be shown
     */
    def predefinedPosts = { attrs ->
        String title = ""

        switch (attrs.postsType) {
            case "recentShares":
                title = "Recent Shares"
                attrs.posts = ListUtil.recentShares
                break
        }

        attrs.title = attrs.title ?: title
        out << posts(attrs)
    }

    /**
     * Allows to mark a post as (Un)Read for logged in user
     *
     * @attr post REQUIRED The type of posts to be shown
     */
    def markRead = { attrs ->
        out << render(
                template: "/templates/commons/post/markRead",
                model: [
                        isRead: session.user ?
                                resourceService.isRead(attrs.post, userService.findByUsername(session.user)) :
                                Boolean.FALSE
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
