<g:if test="${session?.loggedUser != null}">

    <g:set var="resourceId" value="${postDetails.resource.id}"/>

    <span id="markUnread${resourceId}" onclick="ajaxCallToMarkReadController(${resourceId}, 'markUnread')">
        Mark as Unread
    </span>

    <span id="markRead${resourceId}" onclick="ajaxCallToMarkReadController(${resourceId}, 'markRead')">
        Mark as Read
    </span>

    <g:if test="${postDetails.isRead}">
        <g:javascript>
            $("#markUnread${resourceId}").hide();
        </g:javascript>
    </g:if>
    <g:else>
        <g:javascript>
            $("#markRead${resourceId}").hide();
        </g:javascript>
    </g:else>
</g:if>