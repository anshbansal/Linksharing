;
//Default tooltipster options
var tooltipsterOptions = {
    trigger: 'custom',
    onlyOne: false,
    position: 'bottom-right',
    timer: 2500
};

//Apply tooltipster options to all tooltipster forms
$(function () {
    $('.tooltipster-form').find('input').tooltipster(tooltipsterOptions);
});