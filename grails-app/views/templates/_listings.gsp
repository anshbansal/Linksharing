<g:set var="listings" value="${listingDetails.listings}"/>
<g:set var="paginationController" value="${listingDetails.paginationController}"/>
<g:set var="paginationAction" value="${listingDetails.paginationAction}"/>

<g:if test="${listingDetails.searchTerm || listings?.size()}">
    <g:applyLayout name="container" params="[title: listingDetails.title]">

        <content tag="header">
            <g:if test="${listingDetails.searchEnable}">

            <g:set var="searchBoxId" value="search_${listingDetails.idToUpdate}"/>
            <g:set var="searchBoxUrl" value="${createLink(controller: paginationController, action: paginationAction)}"/>

                <span id="${searchBoxId}">
                    <ls:searchBox searchText="${listingDetails.searchTerm}"/>
                    <g:javascript>
                        searchAjaxCallToUpdateId("#${searchBoxId}", "${searchBoxUrl}",
                                "${listingDetails.searchParameters}", "${listingDetails.idToUpdate}");
                    </g:javascript>
                </span>

            </g:if>

            <g:if test="${listingDetails.headerAdditionalTemplate}">
                <g:render template="${listingDetails.headerAdditionalTemplate}"/>
            </g:if>

        </content>

        <g:render template="${listingDetails.renderTemplate}" var="listing" collection="${listings}"
                                    model="[idToUpdateWithPostsOnClick: listingDetails.idToUpdateWithPostsOnClick]"/>
                                    %{--Refer COMMENT_GENERIC_CONTROL_OBJECT in ListingDetails co--}%

        <g:if test="${listingDetails.hasFooter}">
            <content tag="footer">
                <section class="paginationList">
                    <util:remotePaginate
                            pageSizes="[5, 10, 20]"
                            alwaysShowPageSizes="true"
                            update="${listingDetails.idToUpdate}"
                            max="${listingDetails.max}"
                            offset="${listingDetails.offset}"
                            total="${listingDetails.totalListings}"
                            controller="${listingDetails.paginationController}"
                            action="${paginationAction}"
                            params="${listingDetails.paginationParams + [searchTerm: listingDetails.searchTerm]}"/>
                </section>
            </content>
        </g:if>

    </g:applyLayout>
    %{--TODO Pages should be of fixed size for some cases--}%
    %{--TODO pagesSizes need not to be shown if no more and is least--}%
</g:if>