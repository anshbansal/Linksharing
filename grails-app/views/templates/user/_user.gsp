<section class="group">
    <div class="left-part">
        <ls:photo user="${listing}"/>
    </div>
    <div class="left-part">
        <div>${listing.firstName}</div>
        <div>@${listing.username}</div>

        <div class="grid-2">
            <div>
                Subscriptions
            </div>
            <div>
                ${listing.subscriptions.size()}
            </div>
        </div>

        <div class="grid-2">
            <div>
                Topics
            </div>
            <div>
                ${listing.topics.size()}
            </div>
        </div>
    </div>
</section>