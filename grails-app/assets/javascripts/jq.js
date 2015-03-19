//= require jquery
//= require_self

function searchAjaxCallToUpdateId(searchBoxId, ajaxUrl, jsonData, idToUpdate) {

    var searchEndButton = searchBoxId + " .searchEnd";
    var searchTextBox = searchBoxId + " .searchText";

    $(searchEndButton).on('click', function () {
        $.ajax({
            url: ajaxUrl,
            data: $.extend(JSON.parse(jsonData), {searchTerm: $(searchTextBox).val()})
        }).success(function (data, textStatus) {
            $("#" + idToUpdate).html(data);
        });
    })
}

function ajaxCallToMarkReadController(resourceId, readUnread) {
    $.ajax({
        url: markReadURL,
        data: {readUnread: readUnread, resourceId: resourceId}
    }).success(function(data, status) {

        var markReadSpan = $("#markRead" + resourceId);
        var markUnreadSpan = $("#markUnread" + resourceId);

        if (data !== 'Error') {

            if (data == 'markedRead') {
                markReadSpan.hide();
                markUnreadSpan.show();
            } else {
                markReadSpan.show();
                markUnreadSpan.hide();
            }

            $("#inbox").find(".post_id_" + resourceId).hide();
        }
    });
}

function displayEditOptions(currentTopicClass) {
    var currentTopic = $("." + currentTopicClass);
    currentTopic.find(".editTopicEnabled").css('visibility', 'visible');
    currentTopic.find(".editTopicDisabled").css('visibility', 'hidden');
}

function hideEditOptions(currentTopicClass) {
    var currentTopic = $("." + currentTopicClass);
    currentTopic.find(".editTopicEnabled").css('visibility', 'hidden');
    currentTopic.find(".editTopicDisabled").css('visibility', 'visible');
}