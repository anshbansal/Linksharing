//= require config/tooltipster_config
//= require_self

$("#send_invite_form").validate($.extend(true, {}, ajaxSubmitHandler, isEmailPresentRule));
