package br.com.personality.insights.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.Dependent;

@Dependent
public class WarningException extends Exception {

	private static final long serialVersionUID = 3143927489477636392L;

	private List<String> globalMessages;
	private Map<String, String> fieldMessages;

	public WarningException() {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
	}

	public WarningException(String message) {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		addMessage(message);
	}

	public WarningException(List<String> messages) {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		addAllMessages(messages);
	}

	public WarningException(String field, String message) {
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
		addMessage(field, message);
	}

	public WarningException(HashMap<String, String> messages) {
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
		addAllMessages(messages);
	}

	private String getMessageFromResource(String key) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages");
			key = bundle.getString(key);
		} catch (MissingResourceException e) {
		}
		return key;
	}

	public void addMessage(String message) {
		message = getMessageFromResource(message);
		globalMessages.add(message);
	}

	public void addAllMessages(List<String> messages) {
		for (String msg : messages) {
			globalMessages.add(getMessageFromResource(msg));
		}
	}

	public void addMessage(String field, String message) {
		message = getMessageFromResource(message);
		fieldMessages.put(field, message);
	}

	public void addAllMessages(HashMap<String, String> messages) {
		for (String field : messages.keySet()) {
			fieldMessages.put(field, getMessageFromResource(messages.get(field)));
		}
	}

	public List<String> getGlobalMessages() {
		return globalMessages;
	}

	public Map<String, String> getFieldMessages() {
		return fieldMessages;
	}

	public boolean hasMessages() {
		return (globalMessages != null && !globalMessages.isEmpty())
				|| (fieldMessages != null && !fieldMessages.isEmpty());
	}

	public void clean() {
		globalMessages = new ArrayList<String>();
		fieldMessages = new HashMap<String, String>();
	}

}
