package com.ttd.linksharing.vo

import com.ttd.linksharing.util.Mappings
import grails.converters.JSON

class ListingDetails<E> {

    private final String LISTING_TEMPLATE = "/templates/listings"

    String title
    String renderTemplate

    List<E> listings
    Integer totalListings = 0

    String idToUpdate

    Boolean searchEnable
    Boolean paginationDisable

    String paginationController
    String paginationAction

    QueryParameters queryParams

    Long userId
    Long topicId

    void setAttrs(Map attrs) {
        title = attrs.title

        queryParams = new QueryParameters()

        queryParams.max = attrs.int('max') ?: 5
        queryParams.offset = attrs.int('offset') ?: 0

        idToUpdate = attrs.idToUpdate ?: attrs.type
        paginationController = attrs.paginationController
        paginationAction = attrs.paginationAction ?: attrs.type
        paginationDisable =  Boolean.parseBoolean(attrs.paginationDisable)

        searchEnable = Boolean.parseBoolean(attrs.searchEnable)
        queryParams.searchTerm = attrs.searchTerm ?: ""

        userId = Mappings.parseStringOrLong(attrs?.userId)
        topicId = Mappings.parseStringOrLong(attrs?.topicId)
    }

    Integer getMax() { queryParams.max }
    Integer getOffset() { queryParams.offset }
    String getSearchTerm() { queryParams.searchTerm }

    void setIncludePrivates(Boolean includePrivates) {
        queryParams.includePrivates = includePrivates
    }
    void setSortTerm(String sortTerm) {
        queryParams.sortTerm = sortTerm
    }
    void setSortOrder(String sortOrder) {
        queryParams.sortOrder = sortOrder
    }


    void setPaginationController(String paginationController) {
        if (!this.paginationController) {
            this.paginationController = paginationController
        }
    }

    void setTitle(String title) {
        this.title = this.title ?: title
    }

    void setListings(PagedResult<E> pagedResult) {
        this.listings = pagedResult.paginationList
        this.totalListings = pagedResult.totalCount
    }

    Map getRenderMap() {
        [template: LISTING_TEMPLATE,
         model   : [listingDetails: this]]
    }

    Map getPaginationParams() {
        [title: title, userId: userId, topicId: topicId, searchEnable: searchEnable,
         paginationDisable: paginationDisable, idToUpdate: idToUpdate]
    }

    Boolean getHasFooter() {
        !paginationDisable && totalListings >= listings?.size()
    }

    JSON getSearchParameters() {
        paginationParams as JSON
    }
}
