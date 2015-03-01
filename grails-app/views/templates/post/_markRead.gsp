<g:if test="${session.username != null}">
    <g:if test="${isRead}">
        Mark as Unread
    </g:if>
    <g:else>
        Mark as Read
    </g:else>
</g:if>