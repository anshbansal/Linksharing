package com.ttd.linksharing.taglib

import com.ttd.linksharing.domain.Resource
import com.ttd.linksharing.util.ListUtil

class ApplicationTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "ls"

    /**
     * Creates a Post's container.
     *
     * @attr title REQUIRED The title of the Posts' container
     * @attr posts REQUIRED The posts to be shown
     * @attr user The logged in user
     */
    def posts = { attrs ->
        out << render(template: "/templates/posts",
                        model: [
                                title: attrs.title,
                                posts: attrs.posts,
                                user: attrs.user ?: "none"
                        ]
        )
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
