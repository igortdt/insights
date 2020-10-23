function mascara(o, f) {
	v_obj = o;
	v_fun = f;
	setTimeout("execmascara()", 1);
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value);
}

function soNumeros(v) {
	return v.replace(/\D/g, "");
}

/*function telefone(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/^(\d\d)(\d)/g, "($1)$2");
	v = v.replace(/(\d{4})(\d)/, "$1-$2");
	return v;
}*/

function data(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/^(\d{2})(\d)/, "$1/$2");
	v = v.replace(/(\d{2})(\d)/, "$1/$2");
	v = v.replace(/(\d{4})(\d)/, "$1");
	v = v.replace(/(\d{9})(\d)/, "");
	return v;
}

function cpf(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/(\d{3})(\d)/, "$1.$2");
	v = v.replace(/(\d{3})(\d)/, "$1.$2");
	v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
	return v;
}

function cep(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/^(\d{5})(\d)/, "$1-$2");
	return v;
}

function cnpj(v) {
	v = v.replace(/\D/g, "");
	v = v.replace(/^(\d{2})(\d)/, "$1.$2");
	v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
	v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
	v = v.replace(/(\d{4})(\d)/, "$1-$2");
	return v;
}

function cpfcnpj(v) {
	if (v_obj.value.length <= 14) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{3})(\d)/, "$1.$2");
		v = v.replace(/(\d{3})(\d)/, "$1.$2");
		v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
		return v;
	} else {
		v = v.replace(/\D/g, "");
		v = v.replace(/^(\d{2})(\d)/, "$1.$2");
		v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
		v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
		v = v.replace(/(\d{4})(\d)/, "$1-$2");
		return v;
	}
	return null;
}

function telefone(v) {
	if (v_obj.value.length > 15) {
		v = v.substr(0, 15);
		return v;
	} else if (v_obj.value.length <= 14) {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{2})(\d)/, "($1) $2");
		v = v.replace(/(\d{4})(\d)/, "$1-$2");
		return v;
	} else {
		v = v.replace(/\D/g, "");
		v = v.replace(/(\d{2})(\d)/, "($1) $2");
		v = v.replace(/(\d{5})(\d)/, "$1-$2");
		v = v.replace(/(\d{4})(\d{1,2})$/, "$1$2");
		return v;
	}
	return null;
}