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
                <g:link controller="topic" action="topics" params="[topicListType: 'subscriptions', userId: user.id]">
                    ${listing.numSubscriptions}
                </g:link>
            </div>
        </div>

        <div class="grid-2">
            <div>
                Topics
            </div>

            <div>
                <g:link controller="topic" action="topics" params="[topicListType: 'forUser', userId: user.id]">
                    ${listing.numTopics}
                </g:link>
            </div>
        </div>
    </div>
</section>