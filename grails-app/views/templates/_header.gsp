<header class="header">
    <g:link uri="/">Link Sharing</g:link>
    <g:if test="${session.user != null}">
        <nav>
            <div id="currentUserName">${session.user}</div>
            <ul id="nav-menu">
                <li>Profile</li>
                <li>
                    <a href="${createLink(controller: "login", action: "logout")}">Logout</a>
                </li>
            </ul>
        </nav>
    </g:if>
    <g:render template="/templates/commons/search"/>
</header>