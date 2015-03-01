;
$("#login-form").validate();

//Common validation rules
var uniqueIdentifierValidation = {
    remote: {
        url: uniqueIdentifierURL
    }
};

//Common validation messages
var usernameTaken = {
    remote: "This username is already taken"
};
var emailTaken = {
    remote: "This email is already taken"
};

//Specific Form validations
$("#registration-form").validate({
    rules: {
        username: uniqueIdentifierValidation,
        email: uniqueIdentifierValidation
    },
    messages: {
        username: usernameTaken,
        email: emailTaken
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

