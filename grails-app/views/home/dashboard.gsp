<html>
<head>
    <meta name="layout" content="main">
    <title>DashBoard</title>
</head>
<body>
    <section class="major-part right-part">
        <ls:posts postsType="inbox" loggedUser="${loggedUser}"/>
    </section>
    <section class="minor-part left-part">
        <ls:user user="${loggedUser}"/>
    </section>
    <section class="minor-part left-part">
        <ls:topics topicsType="subscriptions" loggedUser="${loggedUser}"/>
    </section>
</body>