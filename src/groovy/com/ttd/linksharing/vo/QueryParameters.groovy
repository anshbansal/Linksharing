package com.ttd.linksharing.vo

class QueryParameters {
    Integer max
    Integer offset

    String searchTerm

    Boolean includePrivates

    Map getQueryMapParams() {
        [max: max, offset: offset]
    }
}
