//= require config/tooltipster_config
//= require_self

$("#create_topic_form").validate($.extend(true, {}, ajaxSubmitHandler, topicNameRule));
