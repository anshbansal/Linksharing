//= require tooltipster_config

//Set jquery validator defaults
$.validator.setDefaults({
    errorPlacement: function (error, element) {
        var newError = $(error).text();

        if (newError !== '') {
            $(element).tooltipster('content', newError);
            $(element).tooltipster('show');
        }
    },
    success: function (label, element) {
        $(element).tooltipster('hide');
    }
});