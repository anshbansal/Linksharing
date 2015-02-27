<html>
<head>
    <meta name="layout" content="main">
    <title>DashBoard</title>
</head>
<body>
    <section class="major-part right-part">
        <ls:posts postsType="inbox"/>
    </section>
    <section class="minor-part left-part">
        <ls:user username="${session.user}"/>
    </section>
    <section class="minor-part left-part">
        <ls:topics topicsType="subscriptions"/>
    </section>
</body>