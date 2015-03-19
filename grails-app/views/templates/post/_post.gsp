<g:set var="post" value="${listing.resource}"/>
<g:set var="creator" value="${post.createdBy}"/>

<section class="group post_id_${post.id}">
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
            <g:render template="/templates/post/markRead" model="[postDetails: listing]"/>
        </div>
    </div>
</section>