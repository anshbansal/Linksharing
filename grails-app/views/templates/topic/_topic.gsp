<%@ page import="com.ttd.linksharing.enums.Visibility; com.ttd.linksharing.enums.Seriousness" %>

<g:set var="topic" value="${listing.topic}"/>
<g:set var="creator" value="${listing.creator}"/>

<section class="group">
    <div>
        <div class="left-part">
            <ls:photo user="${creator}"/>
        </div>
        <div class="right-part">
            <div class="grid-3">
                <div>
                    %{--TODO Needs to be parametrized for topic listing page later--}%
                    <g:link uri="/topic/show/${topic.id}">${topic.name}</g:link>
                </div>
                <div>${creator}</div>
                <div>Subscribe</div>
            </div>

            <div class="grid-3">
                <div>
                    <g:submitButton name="save"/>
                </div>
                <div>Subscriptions</div>
                <div>${listing.numSubscriptions}</div>
            </div>

            <div class="grid-3">
                <div>
                    <g:submitButton name="cancel"/>
                </div>
                <div>Post</div>
                <div>${listing.numResources}</div>
            </div>
        </div>
    </div>

    <div>
        <g:select name="seriousness" from="${Seriousness.values()}"/>
        <g:if test="${topic.createdBy == session?.loggedUser || topic.createdBy.admin}">
            <g:select name="visibility" from="${Visibility.values()}" value="${topic.scope}"/>
            Invite
            Edit
        </g:if>
        Delete
    </div>
</section>