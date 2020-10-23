package br.com.personality.insights.portal.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class ConversationBean implements Serializable {

	private static final long serialVersionUID = 6535089631745680573L;

	@Inject
	private Conversation conversation;

	public void begin() {
		if (conversation.isTransient()) {
			conversation.begin();
		}				
	}

	public void end() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}

}
