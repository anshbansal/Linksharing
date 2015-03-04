//= require config/jquery_config

//Common validation rules
var uniqueIdentifierValidation = {url: uniqueIdentifierURL};

//Specific Form validations
$("#login_form").validate();

$("#registration_form").validate({
    rules: {
        username: {
            remote: uniqueIdentifierValidation
        },
        email: {
            remote: uniqueIdentifierValidation,
            email: true
        },
        rePassword: {
            equalTo: '#password'
        }
    },
    messages: {
        username: {
            remote: "This username is already taken"
        },
        email: {
            remote: "This email is already taken"
        }
    }
});

$("#password-update-form").validate({
    rules: {
        rePassword: {
            equalTo: '#password'
        }
    }
});

$("#details_update_form").validate({
    rules: {
        email: {
            remote: uniqueIdentifierValidation,
            email: true
        }
    },
    messages: {
        email: {
            remote: "This email is already taken"
        }
    }
});