<html>
<head>
    <meta name="layout" content="main">
    <title>User</title>
</head>

<body>
    %{--<section class="minor-part left-part">--}%
        %{--<section class="single-container">--}%
            %{--<ls:user user="${user}"/>--}%
        %{--</section>--}%
    %{--</section>--}%

    %{--<section class="major-part right-part" id="postsForUser">--}%
        %{--<ls:posts type="forUser" userId="${user.id}" searchEnable="true" idToUpdate="postsForUser"/>--}%
    %{--</section>--}%

    <section class="minor-part left-part" id="topicsForUser">
        <ls:topics type="forUser" userId="${user.id}" searchEnable="true" idToUpdate="topicsForUser"/>
    </section>
</body>
</html>