<section class="generic-container">
    <header class="heading">
        <g:pageProperty name="title"/>
    </header>

    <section class="container-contents">
        <g:layoutBody />
    </section>

    <footer class="footer">
        <g:if test="${pageProperty(name: 'page.footer')}">
            <g:pageProperty name="page.footer"/>
        </g:if>
    </footer>

</section>