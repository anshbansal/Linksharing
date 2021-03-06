<html>
<head>
    <meta name="layout" content="main">
    <title>DashBoard</title>
</head>
<body>
    <section class="major-part right-part" id="inbox">
        <ls:posts type="inbox" searchEnable="true"/>
    </section>
    <section class="minor-part">
        <section class="single-container">
            <ls:user user="${session?.loggedUser}"/>
        </section>
    </section>
    <section class="minor-part" id="subscriptions">
        <ls:topics type="subscriptions" searchEnable="true" headerAdditionalTemplate="/templates/commons/view_all_subscriptions"/>
    </section>
    <section class="minor-part left-part" id="trendingTopics">
        <ls:topics type="trendingTopics" paginationDisable="true"/>
    </section>
</body>