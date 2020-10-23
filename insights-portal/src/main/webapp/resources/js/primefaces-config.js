PrimeFaces.locales['pt_BR'] = {  
    closeText: 'Fechar',  
    prevText: 'Anterior',  
    nextText: 'Próximo',  
    currentText: 'Começo',  
    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],  
    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],  
    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],  
    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],  
    dayNamesMin: ['D','S','T','Q','Q','S','S'],  
    weekHeader: 'Semana',  
    firstDay: 1,  
    isRTL: false,  
    showMonthAfterYear: false,  
    yearSuffix: '',  
    timeOnlyTitle: 'Só Horas',  
    timeText: 'Tempo',  
    hourText: 'Hora',  
    minuteText: 'Minuto',  
    secondText: 'Segundo',  
    currentText: 'Data Atual',  
    ampm: false,  
    month: 'Mês',  
    week: 'Semana',  
    day: 'Dia',  
    allDayText : 'Todo Dia'  
};

PrimeFaces.widget.DefaultCommand = PrimeFaces.widget.BaseWidget.extend({
    init: function(cfg) {

        this.cfg = cfg;
        this.id = this.cfg.id;
        this.jqId = PrimeFaces.escapeClientId(this.id);
        this.jqTarget = $(PrimeFaces.escapeClientId(this.cfg.target));
        this.scope = this.cfg.scope ? $(PrimeFaces.escapeClientId(this.cfg.scope)) : null;
        var _self = this;

        //attach keypress listener to parent form
        var form = this.jqTarget.parents('form:first');
        form.off('keydown.' + this.id);
        form.on('keydown.' + this.id, function(e) {
        	var keyCode = $.ui.keyCode;
        	if(e.which == keyCode.ENTER || e.which == keyCode.NUMPAD_ENTER) {
        		//do not proceed if event target is not in this scope or target is a textarea
	           if((_self.scope && _self.scope.find(e.target).length == 0)||$(e.target).is('textarea')) {
	               return true;
	           }

	       _self.jqTarget.click();
               e.preventDefault();
           }
        });

        $(this.jqId + '_s').remove();
    }
});