<%@ page import="com.ttd.linksharing.enums.Seriousness" %>
<section class="group">
    <div>
        <div class="left-part">
            <ls:photo user="${listing.createdBy}"/>
        </div>
        <div class="right-part">
            <div class="grid-3">
                <div>${listing.name}</div>
                <div>${listing.createdBy}</div>
                <div>.</div>
            </div>

            <div class="grid-3">
                <div><g:submitButton name="save"/></div>
                <div>Subscriptions</div>
                <div>
                    ${listing.createdBy.subscriptions.size()}
                </div>
            </div>

            <div class="grid-3">
                <div><g:submitButton name="cancel"/></div>
                <div>Post</div>
                <div>
                    ${listing.resources.size()}
                </div>
            </div>
        </div>
    </div>

    <div>
        <g:select name="seriousness"
                  from="${Seriousness.values()}"
                  value="${listing.createdBy.subscriptions.seriousness}"/>
        Visibility Invite Edit Delete
    </div>
</section>