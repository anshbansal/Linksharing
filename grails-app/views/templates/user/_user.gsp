<g:set var="user" value="${listing.user}"/>

<section class="group user">
    <div class="left-part">
        <ls:photo user="${user}"/>
    </div>
    <div class="left-part">
        <div>${user.firstName}</div>
        <div>@${user.username}</div>

        <div class="grid-2">
            <div>
                Subscriptions
            </div>
            <div>
                ${listing.numSubscriptions}
            </div>
        </div>

        <div class="grid-2">
            <div>
                Topics
            </div>
            <div>
                ${listing.numTopics}
            </div>
        </div>
    </div>
</section>