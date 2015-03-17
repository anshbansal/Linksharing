package com.ttd.linksharing.vo

import com.ttd.linksharing.domain.User

class QueryParameters {
    Integer max
    Integer offset

    String searchTerm

    Long userId
    Long topicId

    User loggedUser

    Map getQueryMapParams() {
        [max: max, offset: offset]
    }


    @Override
    public String toString() {
        return "QueryParameters{" +
                "max=" + max +
                ", offset=" + offset +
                ", searchTerm='" + searchTerm + '\'' +
                ", userId=" + userId +
                ", topicId=" + topicId +
                ", loggedUser=" + loggedUser +
                '}';
    }
}
