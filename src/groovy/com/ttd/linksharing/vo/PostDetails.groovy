package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.ReadingItem
import com.ttd.linksharing.domain.Resource

class PostDetails {

    Resource resource
    Boolean isRead

    static Closure mapFromResource = { List<Resource> resourceList ->
        resourceList.collect([]) { Resource resource ->
            new PostDetails(resource: resource)
        }
    }

    static Closure mapFromReadingItem = { List<ReadingItem> readingItems ->
        readingItems.collect([]) { ReadingItem readingItem ->
            new PostDetails(resource: readingItem.resource, isRead: readingItem.isRead)
        }
    }
}
