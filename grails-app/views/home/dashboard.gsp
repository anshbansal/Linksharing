<html>
<head>
    <meta name="layout" content="main">
    <title>DashBoard</title>
</head>
<body>
    <section class="major-part right-part" id="inbox">
        <ls:posts type="inbox" searchEnable="true"/>
    </section>
    <section class="minor-part left-part">
        <ls:user user="${session?.loggedUser}"/>
    </section>
    <section class="minor-part left-part" id="subscriptions">
        <ls:topics type="subscriptions" searchEnable="true"/>
    </section>
</body>