<section class="post group">
    <div class="left-part">
        <ls:photo user="${post.createdBy}"/>
    </div>

    <div class="details">
        <div class="small-part">
            <span>${post.createdBy.firstName}  @${post.createdBy.username}</span>
            <span class="right-part">${post.topic}</span>
        </div>
        <div>${post.description}</div>
        <div class="small-part">
            Dummy
        </div>
    </div>
</section>