//Related to Nav Menu
var nav_menu = $("#nav-menu");
nav_menu.menu();
nav_menu.toggle();

$("#currentUserName").click(function () {
    nav_menu.toggle();
});


//Related to Popup
var popup = $("#popup");

function createPopup(url) {
    $.ajax({
        url: url
    }).done(function(data) {
        popup.html(data);
        popup.dialog({width: 500});
    });
}

popup.on('click', ".cancelButton", function(e) {
    $(this).closest(popup).dialog('close');
    popup.html("");
    return false;
});

var mainSearchBox = $("#main-search-box");

mainSearchBox.find(".searchEnd").on('click', function () {
    var link = $('#globalSearchLink').html().trim();
    var searchText = mainSearchBox.find('.searchText').val();
    document.location = link + '?searchTerm=' + searchText;
});