;
$("#login-form").validate();

$("#registration-form").validate();


function createTopicPopup(url) {
    getDataAndDisplayPopup(url);
}

function getDataAndDisplayPopup(url) {
    $.ajax({
        url: url
    }).done(function(data) {
        $("#popup").html(data);
        $("#popup").dialog();
    });
}