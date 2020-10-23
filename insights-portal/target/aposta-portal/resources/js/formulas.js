function addCarac(el_id, target) {
	var selector = '#' + el_id + '_input option:selected';
	var tx = jQuery(selector).text();
	var vl = jQuery(selector).val();
	if (vl == '-1') return;
	tx = tx.substr(0, 1);
	vl = 'CRT' + vl;
	addComplexText(target, tx, vl);
}
function addSingleText(target, text) {
	addSingle(target, text);
	disableElements(target);
	checkUndo(target);
}
function addComplexText(target, text, value) {
	addComplex(target, text, value);
	disableElements(target);
	checkUndo(target);
}
function addOperation(target, operation) {
	addSingle(target, operation);
	disableOperations(target);
	checkUndo(target);
}
function addSingle(target, text) {
	var target_id = '#' + target;
	var view_id = '#' + target + '_view';
	var input_id = '#' + target + '_input';
	var tx = jQuery(target_id).val();
	if (tx == '') {
		tx = text;
	} else {
		tx += ' ' + text;
	}
	jQuery(target_id).val(tx);
	tx = jQuery(view_id).val();
	if (tx == '') {
		tx = text;
	} else {
		tx += ' ' + text;
	}
	jQuery(view_id).val(tx);
	jQuery(input_id).val(tx);
}
function addComplex(target, text, val) {
	var target_id = '#' + target;
	var view_id = '#' + target + '_view';
	var input_id = '#' + target + '_input';
	var tx = jQuery(target_id).val();
	if (tx == '') {
		tx = val;
	} else {
		tx += ' ' + val;
	}
	jQuery(target_id).val(tx);
	tx = jQuery(view_id).val();
	if (tx == '') {
		tx = text;
	} else {
		tx += ' ' + text;
	}
	jQuery(view_id).val(tx);
	jQuery(input_id).val(tx);
}
function disableElements(className) {
	var cn = '.' + className + '_el';
	var co = '.' + className + '_op';
	var arr = jQuery(cn);
	for (i = 0; i < arr.length; i++) {
		PF(jQuery(jQuery(cn)[i]).attr('id')).disable();
	}
	arr = jQuery(co);
	for (i = 0; i < arr.length; i++) {
		PF(jQuery(jQuery(co)[i]).attr('id')).enable();
	}
}
function disableOperations(className) {
	var cn = '.' + className + '_el';
	var co = '.' + className + '_op';
	var arr = jQuery(cn);
	for (i = 0; i < arr.length; i++) {
		PF(jQuery(jQuery(cn)[i]).attr('id')).enable();
	}
	arr = jQuery(co);
	for (i = 0; i < arr.length; i++) {
		PF(jQuery(jQuery(co)[i]).attr('id')).disable();
	}
}
function checkUndo(target) {
	var el = '#' + target;
	var btn = target + '_undo'
	var tx = jQuery(el).val();
	if (tx == '') {
		PF(btn).disable();
	} else {
		PF(btn).enable();
	}
}
function undo(target) {
	var el_id = '#' + target;
	var view_id = '#' + target + '_view';
	var input_id = '#' + target + '_input';
	var carac_id = target + '_carac';
	var vl = jQuery(el_id).val();
	var tx = jQuery(view_id).val();
	if (vl == '') return;
	var avl = vl.split(' ');
	if (avl.length == 1) {
		vl = '';
	} else {
		vl = '';
		for (i = 0; i < avl.length - 1; i++) {
			vl += avl[i] + ' ';
		}
		vl = vl.substr(0, vl.length - 1);
	}
	jQuery(el_id).val(vl);
	var atx = tx.split(' ');
	tx = '';
	for (i = 0; i < atx.length - 1; i++) {
		tx += atx[i] + ' ';
	}
	tx = tx.substr(0, tx.length - 1);
	jQuery(view_id).val(tx);
	jQuery(input_id).val(tx);
	if (avl.length % 2 == 0) {
		disableElements(target);
	} else {
		disableOperations(target);
	}
	PF(carac_id).selectValue('-1');
	checkUndo(target);
}