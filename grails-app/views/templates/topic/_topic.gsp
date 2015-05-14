<%@ page import="com.ttd.linksharing.enums.Visibility; com.ttd.linksharing.enums.Seriousness" %>

<g:set var="topic" value="${listing.topic}"/>
<g:set var="creator" value="${listing.creator}"/>
<g:set var="currentTopicClass" value="topic_id_${topic.id}"/>

<g:set var="isUserSubscribedToTopic" value="${ls.isUserSubscribedToTopic([topic: topic])}"/>

<section class="group ${currentTopicClass}">
    <div class="group container-fluid">
        <div class="row">
            <div class="col-md-3">
                <ls:photo user="${creator}"/>
            </div>

            <div class="col-md-9">
                <div class="row">
                    <div class="col-md-4">
                        <span class="editTopicDisabled">
                            <g:if test="${idToUpdateWithPostsOnClick}">
                                <g:set var="curUrl" value="${createLink(controller: 'listingsPost', action: 'forTopic',
                                        params: [searchEnable: true, topicId: topic.id, idToUpdate: idToUpdateWithPostsOnClick])}"/>

                                <span class="topicName"
                                      onclick="getPostsForTopicId('${curUrl}', '${idToUpdateWithPostsOnClick}')">
                                    ${topic.name}
                                </span>

                            </g:if>
                            <g:else>
                                <g:link uri="/topic/show/${topic.id}">
                                    <span class="topicName">
                                        ${topic.name}
                                    </span>
                                </g:link>
                            </g:else>
                        </span>

                        <ls:isAdminOrOwnerOfTopic topic="${topic}">
                            <span class="editTopicEnabled">
                                <g:textField name="topicNameEdit" value="${topic.name}" size="8"/>
                            </span>
                        </ls:isAdminOrOwnerOfTopic>
                    </div>
                    <ls:isAdminOrOwnerOfTopic topic="${topic}">
                        <div class="col-md-4">
                            <span class="editTopicEnabled"
                                  onclick="updateTopicName('${currentTopicClass}', '${topic.id}')">
                                <g:submitButton name="save"/>
                            </span>
                        </div>

                        <div class="col-md-4">
                            <span class="editTopicEnabled" onclick="hideEditOptions('${currentTopicClass}')">
                                <g:submitButton name="cancel"/>
                            </span>
                        </div>
                    </ls:isAdminOrOwnerOfTopic>

                </div>

                <div class="row">
                    <div class="col-md-6">
                        ${creator}
                    </div>

                    <div class="col-md-4">Subscriptions</div>

                    <div class="col-md-2">Post</div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <ls:isNotOwnerOfTopic topic="${topic}">
                            <span class="subscribeToTopic" onclick="subscribeToTopic('${topic.id}')">
                                Subscribe
                            </span>
                            <span class="unsubscribeFromTopic" onclick="unsubscribeFromTopic('${topic.id}')">
                                UnSubscribe
                            </span>
                        </ls:isNotOwnerOfTopic>
                    </div>

                    <div class="col-md-4">${listing.numSubscriptions}</div>

                    <div class="col-md-2">${listing.numResources}</div>
                </div>

            </div>
        </div>
    </div>

    <div>
        <span class="unsubscribeFromTopic">
            <g:select name="seriousness" from="${Seriousness.values()}"
                      onchange="updateSeriousness('${currentTopicClass}', '${topic.id}')"/>
        </span>

        <ls:isAdminOrOwnerOfTopic topic="${topic}">
            <g:select name="visibility" from="${Visibility.values()}" value="${topic.scope}"
                      onchange="updateVisibility('${currentTopicClass}', '${topic.id}')"/>
            <a onclick="displayEditOptions('${currentTopicClass}')">Edit</a>
            <a onclick="deleteTopic('${topic.id}')">Delete</a>
        </ls:isAdminOrOwnerOfTopic>
        <g:if test="${isUserSubscribedToTopic}">
            <a onclick="createPopup(sendInvitationTemplateURL)">Invite</a>
        </g:if>
    </div>

    <g:if test="${isUserSubscribedToTopic}">
        <g:javascript>
            showUnsubscribeFromTopic('${currentTopicClass}');
        </g:javascript>
    </g:if>
    <g:else>
        <g:javascript>
            showSubscribeToTopic('${currentTopicClass}');
        </g:javascript>
    </g:else>
</section>