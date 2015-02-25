<section class="post group">
    <div class="left-part">
        <ls:photo user="${listing.createdBy}"/>
    </div>

    <div class="details">
        <div class="small-part">
            <span>${listing.createdBy.firstName}  @${listing.createdBy.username}</span>
            <span class="right-part">${listing.topic}</span>
        </div>
        <div>${listing.description}</div>
        <div class="small-part">
            <ls:resourceType post="${listing}"/>
            <ls:markRead post="${listing}"/>
        </div>
    </div>
</section>