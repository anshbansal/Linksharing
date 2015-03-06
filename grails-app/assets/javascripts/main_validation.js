//= require config/jquery_validation_config

//Common validation rules
var emailRule = {
    rules: {
        email: {
            remote: {url: uniqueIdentifierURL},
            email: true
        }
    },
    messages: {
        email: {
            remote: "This email is already taken"
        }
    }
};

var usernameRule = {
    rules: {
        username: {
            remote: {url: uniqueIdentifierURL}
        }
    },
    messages: {
        username: {
            remote: "This username is already taken"
        }
    }
};

var rePasswordRule = {
    rules: {
        rePassword: {
            equalTo: '#password'
        }
    }
};

var topicNameRule = {
    rules: {
        topicName: {
            remote: {url: topicNameURL}
        }
    },
    messages: {
        topicName: {
            remote: "This topic is already present for the current user"
        }
    }
};

//Common functions
function showResponse(data) { alert(data) }

var ajaxSubmitHandler = {
    submitHandler: function (form) {
        $(form).ajaxSubmit({
            success: showResponse,
            error: showResponse
        });
        return false;
    }
};

//Specific Form validations
$("#login_form").validate();

$("#registration_form").validate($.extend(true, {}, rePasswordRule, emailRule, usernameRule));

$("#password_update_form").validate($.extend(true, {}, ajaxSubmitHandler, rePasswordRule));

$("#details_update_form").validate(emailRule);