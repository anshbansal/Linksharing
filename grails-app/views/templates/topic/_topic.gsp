<%@ page import="com.ttd.linksharing.enums.Visibility; com.ttd.linksharing.enums.Seriousness" %>

<g:set var="topic" value="${listing.topic}"/>
<g:set var="creator" value="${listing.creator}"/>
<g:set var="currentTopicClass" value="topic_id_${topic.id}"/>

<section class="group ${currentTopicClass}">
    <div class="group">
        <div class="left-part">
            <ls:photo user="${creator}"/>
        </div>

        <div class="right-part">
            <div class="grid-3">
                <div>
                    <span class="editTopicDisabled">
                    %{--TODO Needs to be parametrized for topic listing page later--}%
                        <g:link uri="/topic/show/${topic.id}">
                            <span class="topicName">
                                ${topic.name}
                            </span>
                        </g:link>
                    </span>

                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <span class="editTopicEnabled">
                            %{--TODO add topic name edit field--}%
                            %{--${topic.name}--}%
                            <g:textField name="topicNameEdit" value="${topic.name}" size="8"/>
                        </span>
                    </ls:isAdminOrOwnerOfTopic>
                </div>

                <div>${creator}</div>

                <div>
                    <ls:isNotOwnerOfTopic topic="${topic}">
                        Subscribe
                    </ls:isNotOwnerOfTopic>
                </div>
            </div>

            <div class="grid-3">
                <div>
                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <span class="editTopicEnabled" onclick="updateTopicName('${currentTopicClass}', '${topic.id}')">
                            <g:submitButton name="save"/>
                        </span>
                        <span class="ediTopicDisabled"></span>
                    </ls:isAdminOrOwnerOfTopic>
                </div>

                <div>Subscriptions</div>

                <div>${listing.numSubscriptions}</div>
            </div>

            <div class="grid-3">
                <div>
                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <span class="editTopicEnabled" onclick="hideEditOptions('${currentTopicClass}')">
                            <g:submitButton name="cancel"/>
                        </span>
                        <span class="ediTopicDisabled"></span>
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
            <span onclick="displayEditOptions('${currentTopicClass}')">Edit</span>
            <span onclick="deleteTopic('${topic.id}')">Delete</span>
        </ls:isAdminOrOwnerOfTopic>

        Invite
    </div>
</section>