$("#forgot_password").on('click', function () {
    var uniqueIdentifier = $("#uniqueIdentifier");

    if (!uniqueIdentifier.val()) {
        alert("Enter Email/Username");
    } else {
        $.post(resetPasswordURL, {uniqueIdentifier: uniqueIdentifier.val()})
            .done(function () {
                alert("Password reset done. Check email.");
            })
            .fail(function () {
                alert("There was some error in resetting password");
            });
    }
});