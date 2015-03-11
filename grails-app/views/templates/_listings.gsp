<g:set var="listings" value="${listingDetails.listings}"/>
<g:set var="paginationAction" value="${listingDetails.paginationAction}"/>

<g:if test="${listings?.size()}">
    <g:applyLayout name="container" params="[title: listingDetails.title]">

        <content tag="header">
            Aseem
        </content>

        <g:render template="${listingDetails.renderTemplate}" var="listing" collection="${listings}"/>

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
                            params="${listingDetails.paginationParams}"/>
                </section>
            </content>
        </g:if>

    </g:applyLayout>
    %{--TODO For search pagination params will be used for search true. Search results will contain --}%
    %{--TODO Pages should be of fixed size for some cases--}%
    %{--TODO pagesSizes need not to be shown if no more and is least--}%
</g:if>