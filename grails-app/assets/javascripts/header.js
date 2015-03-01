var nav_menu = $("#nav-menu");
nav_menu.menu();
nav_menu.toggle();

$("#currentUserName").click(function () {
    nav_menu.toggle();
});