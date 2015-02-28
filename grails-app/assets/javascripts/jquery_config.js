;
//Default tooltipster options
var tooltipsterOptions = {
    trigger: 'custom',
    onlyOne: false,
    position: 'bottom-right',
    timer: 1000
};

//Apply tooltipster options to all tooltipster forms
$(function () {
    $('.tooltipster-form').find('input').tooltipster(tooltipsterOptions);
});


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
