//= require config/tooltipster_config
//= require_self

//Common validation rules
var topicNameValidation = {url: topicNameURL};

$("#create_topic_form").validate({
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
