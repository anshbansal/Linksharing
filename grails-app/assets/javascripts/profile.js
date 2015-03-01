;
$("#password-update-form").validate({
    rules: {
        rePassword: {
            equalTo: '#password'
        }
    }
});

$("#details-update-form").validate({
    rules: {
        username: {
            remote: {
                url: uniqueIdentifierURL
            }
        }
    },
    messages: {
        username: {
            remote: "This username is already taken"
        }
    }
});