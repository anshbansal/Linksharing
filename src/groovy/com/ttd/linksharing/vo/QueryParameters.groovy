package com.ttd.linksharing.vo

class QueryParameters {
    Integer max
    Integer offset

    String searchTerm

    Boolean includePrivates

    String sortTerm
    String sortOrder

    Map getQueryMapParams() {
        Map result = [max: max, offset: offset]
        if (sortTerm) {
            result += [sortTerm: sortTerm, sortOrder: sortOrder]
        }
        result
    }
}
