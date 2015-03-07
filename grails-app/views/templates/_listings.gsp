<g:set var="listings" value="${listingDetails?.listings}"/>
<g:set var="remoteAttrs" value="${listingDetails.remoteAttrs}"/>

<g:if test="${listings.size()}">
    <g:applyLayout name="container" params="[title: listingDetails?.title]">
        <g:render template="${listingDetails?.renderTemplate}" var="listing" collection="${listings}"/>
    </g:applyLayout>

    <util:remotePaginate pageSizes="[5, 10, 20]" update="${remoteAttrs['update']}" maxsteps="5"
        total="100" action="as"/>

    %{--TODO Listings size/ControllerName/ActionName--}%
</g:if>