package com.ttd.linksharing.vo

class QueryParameters {
    Integer max
    Integer offset

    String searchTerm
    Boolean includePrivates
    String sortBy

    Map getQueryMapParams() {
        [max: max, offset: offset]
    }
}
