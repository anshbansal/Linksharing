package com.ttd.linksharing.vo

class ListingDetails<E> {

    private final String LISTING_TEMPLATE = "/templates/listings"

    String title
    String renderTemplate

    List<E> listings
    Integer totalListings = 0

    String idToUpdate
    Boolean paginationDisable
    String paginationController
    String paginationAction

    Integer max
    Integer offset

    void setAttrs(Map attrs) {
        title = attrs.title

        max = attrs.int('max') ?: 5
        offset = attrs.int('offset') ?: 0

        idToUpdate = attrs.type
        paginationController = attrs.paginationController
        paginationAction = attrs.paginationAction ?: attrs.type
        paginationDisable =  Boolean.parseBoolean(attrs.paginationDisable)
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
}
