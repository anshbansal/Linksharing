var popup = $("#popup");
var nav_menu = $("#nav-menu");
nav_menu.menu();
nav_menu.toggle();

$("#currentUserName").click(function () {
    nav_menu.toggle();
});

//Functions for the common popups
function createTopicPopup(url) {
    getDataAndDisplayPopup(url);
}

function getDataAndDisplayPopup(url) {
    $.ajax({
        url: url
    }).done(function(data) {
        popup.html(data);
        popup.dialog();
    });
}