package com.ttd.linksharing.vo

class ListingDetails<E> {

    private final String LISTING_TEMPLATE = "/templates/listings"

    String title
    String renderTemplate

    List<E> listings
    Integer totalListings = 0

    Map remoteAttrs = [:]
    String paginationController
    String paginationAction

    Integer max
    Integer offset

    void setAttrs(Map attrs) {
        title = attrs.title

        max = attrs.int('max') ?: 5
        offset = attrs.int('offset') ?: 0
        paginationController = attrs.paginationController
        paginationAction = attrs.paginationAction

        remoteAttrs['update'] = attrs.type
    }

    void setPaginationController(String paginationController) {
        if (!this.paginationController) {
            this.paginationController = paginationController
        }
    }

    void setPaginationAction(String paginationAction) {
        if (!this.paginationAction) {
            this.paginationAction = paginationAction
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
}
