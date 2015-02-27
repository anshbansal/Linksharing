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
		<g:layoutHead/>
	</head>
	<body>
        <g:render template="/templates/header" />
        <section id="main-contents">
		    <g:layoutBody/>
        </section>
        <asset:javascript src="application.js"/>
	</body>
</html>
