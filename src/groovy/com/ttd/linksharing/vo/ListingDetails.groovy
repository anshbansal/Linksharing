package com.ttd.linksharing.vo

import com.ttd.linksharing.util.Mappings
import grails.converters.JSON

class ListingDetails<E> {

    private final String LISTING_TEMPLATE = "/templates/listings"

    String title
    String renderTemplate
    String headerAdditionalTemplate

    List<E> listings
    Integer totalListings = 0

    String idToUpdate

    /*
    COMMENT_GENERIC_CONTROL_OBJECT
    This property is specific to Topics listing - Should actually be in a topic specific control object.
    Meaning that this class needs a second generic parameter which should be containing any
    control parameter specific to the type of listing
    I am not doing that because the listing specific control parameter is needed currently for topics listing only
    Even even one more type of listing needed any specific control parameter than 2nd generic parameter should be added
    These control objects should implement a common interface for setting attrs like setAttrs here for consistency
    Change will be needed in the template for passing the generic control object only.
     */
    String idToUpdateWithPostsOnClick

    Boolean searchEnable
    Boolean paginationDisable

    String paginationController
    String paginationAction

    @Delegate QueryParameters queryParams

    void setAttrs(Map attrs) {
        title = attrs.title
        headerAdditionalTemplate = attrs.headerAdditionalTemplate

        queryParams = new QueryParameters()

        max = attrs.int('max') ?: 5
        offset = attrs.int('offset') ?: 0

        idToUpdateWithPostsOnClick = attrs.idToUpdateWithPostsOnClick

        idToUpdate = attrs.idToUpdate ?: attrs.type
        paginationController = attrs.paginationController
        paginationAction = attrs.paginationAction ?: attrs.type
        paginationDisable =  Boolean.parseBoolean(attrs.paginationDisable)

        searchEnable = Boolean.parseBoolean(attrs.searchEnable)
        searchTerm = attrs.searchTerm ?: ""

        userId = Mappings.parseStringOrLong(attrs?.userId)
        topicId = Mappings.parseStringOrLong(attrs?.topicId)
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
         paginationDisable: paginationDisable, idToUpdate: idToUpdate, idToUpdateOnClick: idToUpdateWithPostsOnClick]
    }

    Boolean getHasFooter() {
        !paginationDisable && totalListings >= listings?.size()
    }

    JSON getSearchParameters() {
        paginationParams as JSON
    }
}
