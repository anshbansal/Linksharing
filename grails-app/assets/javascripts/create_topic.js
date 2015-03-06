//= require config/tooltipster_config
//= require_self

//Common validation rules
var topicNameValidation = {url: topicNameURL};
var create_topic_form = $("#create_topic_form");


create_topic_form.validate({
    rules: {
        topicName: {
            remote: topicNameValidation
        }
    },
    messages: {
        topicName: {
            remote: "This topic is already present for the current user"
        }
    }
});

$(".cancelButton").on('click' , function(e) {
    $(this).closest(popup).dialog('close');
    popup.html("");
    return false;
});