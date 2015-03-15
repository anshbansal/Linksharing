package com.ttd.linksharing.vo

import grails.gorm.PagedResultList

class PagedResult<E> {

    List<E> paginationList
    Integer totalCount

    int size() {
        paginationList.size()
    }

    PagedResult<E> setPaginationList(List<PagedResultList> pagedResultList, Closure transform) {
        this.paginationList = transform(pagedResultList)
        this.totalCount = pagedResultList.totalCount
        return this
    }
}
