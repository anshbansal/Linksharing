package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.util.ListUtil

class ApplicationTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    /**
     * Creates a Generic Listings' container.
     *
     * @attr title REQUIRED The title of the Posts' container
     * @attr posts REQUIRED The posts to be shown
     * @attr template REQUIRED The template of the listing to be rendered
     * @attr user The logged in user
     */
    def listings = { attrs ->
        out << render(
                template: "/templates/listings",
                model: [
                        renderTemplate: attrs.template,
                        title: attrs.title,
                        listings: attrs.listings,
                        user: attrs.user ?: "none"
                ]
        )
    }


    /**
     * Creates a Post's container.
     *
     * @attr title REQUIRED The title of the Posts' container
     * @attr posts REQUIRED The posts to be shown
     * @attr user The logged in user
     */
    def posts = { attrs ->
        attrs.template = "/templates/post"
        attrs.listings = attrs.posts

        out << listings(attrs)
    }

    /**
     * Creates a Post's container as per predefined post types
     *
     * @attr title The title of the Posts' container
     * @attr postsType REQUIRED The type of posts to be shown
     * @attr user The logged in user
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

    //TODO Test User image present
    def photo = { attrs ->
        out << render(template: "/templates/commons/photo",
                        model: [user: attrs.user]
        )
    }
}
