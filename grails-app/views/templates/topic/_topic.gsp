<%@ page import="com.ttd.linksharing.enums.Visibility; com.ttd.linksharing.enums.Seriousness" %>

<g:set var="topic" value="${listing.topic}"/>
<g:set var="creator" value="${listing.creator}"/>

<section class="group topic_id_${topic.id}">
    <div>
        <div class="left-part">
            <ls:photo user="${creator}"/>
        </div>

        <div class="right-part">
            <div class="grid-3">
                <div>
                    <span class="editTopicDisable">
                        %{--TODO Needs to be parametrized for topic listing page later--}%
                        <g:link uri="/topic/show/${topic.id}">${topic.name}</g:link>
                    </span>

                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <span class="editTopic" hidden="hidden">
                            %{--TODO add topic name edit field--}%
                            ${topic.name}
                        </span>
                    </ls:isAdminOrOwnerOfTopic>
                </div>

                <div>${creator}</div>

                <div>
                    <ls:isNotOwnerOfTopic topic="${topic}">
                        Subscribe
                    </ls:isNotOwnerOfTopic>
                    <span> </span>
                </div>
            </div>

            <div class="grid-3">
                <div>
                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <span class="editTopic" hidden="hidden">
                            <g:submitButton name="save"/>
                        </span>
                    </ls:isAdminOrOwnerOfTopic>
                </div>

                <div>Subscriptions</div>

                <div>${listing.numSubscriptions}</div>
            </div>

            <div class="grid-3">
                <div>
                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <span class="editTopic" hidden="hidden">
                            <g:submitButton name="cancel"/>
                        </span>
                    </ls:isAdminOrOwnerOfTopic>
                </div>

                <div>Post</div>

                <div>${listing.numResources}</div>
            </div>
        </div>
    </div>

    <div>
        <g:select name="seriousness" from="${Seriousness.values()}"/>

        <ls:isAdminOrOwnerOfTopic topic="${topic}">
            <g:select name="visibility" from="${Visibility.values()}" value="${topic.scope}"/>
            Edit
            Delete
        </ls:isAdminOrOwnerOfTopic>

        Invite
    </div>

    <g:javascript>

    </g:javascript>
</section>