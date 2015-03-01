<%@ page import="com.ttd.linksharing.enums.Visibility; com.ttd.linksharing.enums.Seriousness" %>

<g:set var="topic" value="${listing}"/>
<g:set var="creator" value="${topic.createdBy}"/>

<section class="group">
    <div>
        <div class="left-part">
            <ls:photo user="${creator}"/>
        </div>
        <div class="right-part">
            <div class="grid-3">
                <div>${topic.name}</div>
                <div>${creator}</div>
                <div>.</div>
            </div>

            <div class="grid-3">
                <div><g:submitButton name="save"/></div>
                <div>Subscriptions</div>
                <div>
                    %{--${topic.subscriptions.size()}--}%
                </div>
            </div>

            <div class="grid-3">
                <div><g:submitButton name="cancel"/></div>
                <div>Post</div>
                <div>
                    %{--${topic.resources.size()}--}%
                </div>
            </div>
        </div>
    </div>

    <div>
        <g:select name="seriousness" from="${Seriousness.values()}"/>
        <g:select name="visibility" from="${Visibility.values()}"/>
        Invite
        Edit
        Delete
    </div>
</section>