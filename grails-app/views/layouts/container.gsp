<section class="generic-container">
    <header class="heading">
        <g:if test="${pageProperty(name: 'title')}">
            <g:pageProperty name="title"/>
        </g:if>
        <g:else>
            <g:pageProperty name="page.title"/>
        </g:else>

        <g:pageProperty name="page.header"/>
    </header>

    <section class="container-contents">
        <g:layoutBody />
    </section>

    <g:if test="${pageProperty(name: 'page.footer')}">
        <footer class="footer">
            <g:pageProperty name="page.footer"/>
        </footer>
    </g:if>

</section>