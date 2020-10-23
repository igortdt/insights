jQuery('.money').priceFormat({
	prefix: 'R$ ',
    centsSeparator: ',',
    thousandsSeparator: '.'
});
jQuery('.number').priceFormat({
	prefix: '',
    centsSeparator: ',',
    thousandsSeparator: '.'
});
jQuery('.indice').priceFormat({
	prefix: '',
    centsSeparator: ',',
    thousandsSeparator: '',
    centsLimit: 8
});
jQuery('.intNumber').keypress(function(evt) {
	evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 35 || charCode == 36 || charCode == 37 || charCode == 39 || charCode == 46) {
    	return true;
    }
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
});
jQuery('.coord').keypress(function(evt) {
	evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 45 || charCode == 46) {
    	return true;
    }
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
});