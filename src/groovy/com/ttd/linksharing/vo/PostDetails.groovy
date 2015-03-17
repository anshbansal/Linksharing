package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource

class PostDetails {

    Resource resource
    Boolean isRead

    static Closure<List<PostDetails>> mapFromResource = { List<Resource> resourceList ->
        List<PostDetails> postsDetail = []
        resourceList.collect(postsDetail) { Resource resource ->
            new PostDetails(resource: resource)
        }
        postsDetail
    }

    static Closure<List<PostDetails>> mapFromReadingItem = { List<ReadingItem> readingItems ->
        List<PostDetails> postsDetail = []
        readingItems.collect(postsDetail) { ReadingItem readingItem ->
            new PostDetails(resource: readingItem.resource, isRead: readingItem.isRead)
        }
        postsDetail
    }
}
