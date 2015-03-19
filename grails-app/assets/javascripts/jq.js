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
    currentTopic.find('input:text').select();
}

function hideEditOptions(currentTopicClass) {
    var currentTopic = $("." + currentTopicClass);
    currentTopic.find(".editTopicEnabled").css('visibility', 'hidden');
    currentTopic.find(".editTopicDisabled").css('visibility', 'visible');
}

function updateTopicName(currentTopicClass, topicId) {
    var currentTopic = $("." + currentTopicClass);
    var newTopicName = currentTopic.find('input:text').val();
    var topicName = currentTopic.find('.topicName');

    $.ajax({
        url: updateTopicNameURL,
        data: {newTopicName: newTopicName, topicId: topicId}
    }).success(function(data, status) {
        topicName.html(newTopicName);
    });

    hideEditOptions(currentTopicClass);
}

function deleteTopic(topicId) {
    $.ajax({
        url: deleteTopicURL,
        data: {topicId: topicId}
    }).success(function(data, status) {
        location.reload(true);
    });
}

function updateVisibility(currentTopicClass, topicId) {
    var currentTopic = $("." + currentTopicClass);
    var selectValue = currentTopic.find('#visibility').val();

    console.log(selectValue);
    $.ajax({
       url: updateTopicVisibilityURL,
        data: {'topic.id': topicId, newVisibility: selectValue}
    });
}

function updateSeriousness(currentTopicClass, topicId) {
    var currentTopic = $("." + currentTopicClass);
    var selectValue = currentTopic.find('#seriousness').val();

    console.log(selectValue);
    $.ajax({
        url: updateTopicSeriousnessURL,
        data: {'topic.id': topicId, newSeriousness: selectValue}
    })
}

function showSubscribeToTopic(currentTopicClass) {
    var currentTopic = $("." + currentTopicClass);

    currentTopic.find('.subscribeToTopic').show();
    currentTopic.find('.unsubscribeFromTopic').hide();
}

function showUnsubscribeFromTopic(currentTopicClass) {
    var currentTopic = $("." + currentTopicClass);

    currentTopic.find('.subscribeToTopic').hide();
    currentTopic.find('.unsubscribeFromTopic').show();
}

function subscribeToTopic(topicId) {
    $.ajax({
        url: subscribeToTopicURL,
        data: {'topic.id': topicId}
    }).success(function() {
        location.reload(true);
    });
}

function unsubscribeFromTopic(topicId) {
    $.ajax({
        url: unsubscribeFromTopicURL,
        data: {'topic.id': topicId}
    }).success(function () {
        location.reload(true);
    });
}