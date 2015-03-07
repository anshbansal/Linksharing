package com.ttd.linksharing.vo

class ListingDetails<E> {

    private final String LISTING_TEMPLATE = "/templates/listings"

    String title
    String renderTemplate

    List<E> listings
    Integer totalListings = 0

    Map remoteAttrs = [:]
    String controllerName
    String actionName

    void setAttrs(Map attrs) {
        title = attrs.title
        remoteAttrs['update'] = attrs.type
    }

    void setTitle(String title) {
        this.title = this.title ?: title
    }

    Map getRenderMap() {
        [template: LISTING_TEMPLATE,
         model   : [listingDetails: this]]
    }
}
