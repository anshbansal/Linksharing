package com.ttd.linksharing.util

import com.ttd.linksharing.domain.Resource

class ListUtil {

    //TODO Depending on User and topic type change results

    static List<Resource> getRecentShares() {
        return Resource.list(
                sort: 'dateCreated', order: 'desc',
                max: 5
        )
    }

    static List<Resource> getTopPosts() {
    }

}
