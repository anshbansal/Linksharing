<g:set var="listings" value="${listingDetails?.listings}"/>
<g:set var="remoteAttrs" value="${listingDetails.remoteAttrs}"/>

<g:if test="${listings.size()}">
    <g:applyLayout name="container" params="[title: listingDetails?.title]">
        <g:render template="${listingDetails?.renderTemplate}" var="listing" collection="${listings}"/>

        <content tag="footer">
            <section class="paginationList">
                <util:remotePaginate
                        pageSizes="[5, 10, 20]"
                        update="${remoteAttrs['update']}"
                        max="${listingDetails.max}"
                        offset="${listingDetails.offset}"
                        total="${listingDetails.totalListings}"
                        controller="${listingDetails.paginationController}"
                        action="${listingDetails.paginationAction}"
                        params="[]"/>
            </section>
        </content>
    </g:applyLayout>
    %{--TODO Later For search pagination remember to generify actionName to searchResults--}%
</g:if>