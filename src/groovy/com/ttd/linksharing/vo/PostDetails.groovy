package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.Resource

class PostDetails {

    Resource resource
    Boolean isRead

    static Closure mapFromResource = { List<Resource> resourceList ->
        resourceList.collect([]) { Resource resource ->
            new PostDetails(resource: resource)
        }
    }
}
