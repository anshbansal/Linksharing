package com.ttd.linksharing.vo

import grails.gorm.PagedResultList

class PagedResult<E> {

    List<E> paginationList
    Integer totalCount

    int size() {
        paginationList.size()
    }
}
