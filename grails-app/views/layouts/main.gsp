<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <title><g:layoutTitle default="Linksharing"/></title>

  		<asset:stylesheet src="application.css"/>
        <asset:javascript src="jq.js"/>
		<g:layoutHead/>
	</head>
	<body>
        <g:render template="/templates/header"/>
        <section id="main-contents">
		    <g:layoutBody/>
        </section>

        <g:javascript>
            var uniqueIdentifierURL = "${createLink(controller:'user',action:'isUniqueIdentifierValid')}";
            var isEmailPresentURL = "${createLink(controller: 'user', action: 'isEmailPresent')}";
            var createTopicTemplateURL = "${createLink(controller: 'topic')}";
            var createLinkResourceTemplateURL = "${createLink(controller: 'linkResource')}";
            var createDocumentResourceTemplateURL = "${createLink(controller: 'documentResource')}";
            var sendInvitationTemplateURL = "${createLink(controller: 'invitation')}";
            var topicNameURL = "${createLink(controller: "topic", action: "isTopicPresent")}";
            var resetPasswordURL = "${createLink(controller: "user", action: "resetPassword")}";
            var markReadURL = "${createLink(controller: 'readingItem', action: 'markAsRead')}";
            var updateTopicNameURL = "${createLink(controller: 'topic', action: 'updateTopicName')}";
            var deleteTopicURL = "${createLink(controller: 'topic', action: 'delete')}";
            var updateTopicSeriousnessURL = "${createLink(controller: 'topic', action: 'updateTopicSeriousness')}";
            var updateTopicVisibilityURL = "${createLink(controller: 'topic', action: 'updateTopicVisibility')}";
            var subscribeToTopicURL = "${createLink(controller: 'subscription', action: 'subscribeToTopic')}";
            var unsubscribeFromTopicURL = "${createLink(controller: 'subscription', action: 'unsubscribeFromTopic')}";
        </g:javascript>
        <asset:javascript src="application.js"/>
	</body>
</html>
