package com.ttd.linksharing.taglib

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
}
