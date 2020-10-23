package br.com.personality.insights.portal.sessao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.personality.insights.exception.AppException;
import br.com.personality.insights.portal.message.MessagesBean;

@Named
@SessionScoped
public class SessaoBean implements Serializable {

	private static final long serialVersionUID = 1720708696238111690L;

	@Inject
	private AppException appException;

	@Inject
	private MessagesBean messagesBean;

	
	

	@PostConstruct
	public void init() {
		
	}

	

	
}
