;
//Common validation rules
var uniqueIdentifierValidation = {
    remote: {
        url: uniqueIdentifierURL
    }
};

//Specific Form validations
var login_form = $("#login_form").validate();

$("#registration_form").validate({
    rules: {
        username: {
            remote: {
                url: uniqueIdentifierURL
            }
        },
        email: {
            remote: {
                url: uniqueIdentifierURL
            },
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

$("#details-update-form").validate({
    rules: {
        username: uniqueIdentifierValidation
    },
    messages: {
        username: usernameTaken
    }
});

