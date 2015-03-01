<g:set var="post" value="${listing.resource}"/>
<g:set var="creator" value="${post.createdBy}"/>

<section class="group">
    <div class="left-part">
        <ls:photo user="${creator}"/>
    </div>

    <div class="details">
        <div class="small-part">
            <span>${creator.firstName}  @${creator.username}</span>
            <span class="right-part">${post.topic}</span>
        </div>
        <div>${post.description}</div>
        <div class="small-part">
            <ls:resourceType post="${post}"/>
            <g:if test="${loggedUser}">
                <g:render template="/templates/post/markRead" model="[isRead: listing.isRead]"/>
            </g:if>
        </div>
    </div>
</section>