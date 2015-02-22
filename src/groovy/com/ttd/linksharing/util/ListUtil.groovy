package com.ttd.linksharing.util

import com.ttd.linksharing.domain.Resource

class ListUtil {

    //TODO Depending on User and topic type change results

    static List<Resource> getRecentShares() {
        //TODO Posts of public topics will be shown only
        return Resource.list(
                sort: 'dateCreated', order: 'desc',
                max: 5
        )
    }

}
