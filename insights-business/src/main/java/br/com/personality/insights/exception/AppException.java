package br.com.personality.insights.exception;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.Dependent;

@Dependent
public class AppException extends Exception {

	private static final long serialVersionUID = 7573118222245327608L;

	private List<String> globalMessages;
	private Map<String, String> fieldMessages;

	public AppException() {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
	}

	public AppException(String error) {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		addError(error);
	}

	public AppException(String error, Object... params) {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		addError(error, params);
	}

	public AppException(List<String> errors) {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		addAllErrors(errors);
	}

	public AppException(Exception exception) {
		if (globalMessages == null) {
			globalMessages = new ArrayList<String>();
		}
		addError(exception);
	}

	public AppException(String field, String error, Object... params) {
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
		addError(field, error, params);
	}

	public AppException(HashMap<String, String> errors) {
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
		addAllErrors(errors);
	}

	public AppException(String field, Exception exception) {
		if (fieldMessages == null) {
			fieldMessages = new HashMap<String, String>();
		}
		addError(field, exception);
	}

	public String getMessageFromResource(String key, Object... params) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages");
			key = bundle.getString(key);
			Object[] p = new String[params.length];
			//MessageFormat m = new MessageFormat(key);
			for (int i = 0; i < params.length; i++) {
				try {
					p[i] = bundle.getString(params[i].toString());		
					//key = key.replace("{"+i+"}", params[i].toString());
					//key = MessageFormat.format(key, params[i].toString());
				} catch (MissingResourceException e) {
					p[i] = params[i];
				}
			} 
			return MessageFormat.format(key, p);
		} catch (MissingResourceException e) {
		}
		return key;
	}

	public void addError(String error) {
		error = getMessageFromResource(error);
		globalMessages.add(error);
	}

	public void addError(String error, Object... params) {
		error = getMessageFromResource(error, params);
		globalMessages.add(error);
	}

	public void addAllErrors(List<String> errors) {
		for (String error : errors) {
			globalMessages.add(getMessageFromResource(error));
		}
	}

	public void addError(Exception exception) {
		globalMessages.add(exception.getLocalizedMessage());
	}

	public void addError(String field, String error, Object... params) {
		error = getMessageFromResource(error, params);
		fieldMessages.put(field, error);
	}

	public void addAllErrors(HashMap<String, String> errors) {
		for (String field : errors.keySet()) {
			fieldMessages.put(field, getMessageFromResource(errors.get(field)));
		}
	}

	public void addError(String field, Exception exception) {
		fieldMessages.put(field, exception.getLocalizedMessage());
	}

	public List<String> getGlobalMessages() {
		return globalMessages;
	}

	public Map<String, String> getFieldMessages() {
		return fieldMessages;
	}

	public boolean hasErrors() {
		return (globalMessages != null && !globalMessages.isEmpty())
				|| (fieldMessages != null && !fieldMessages.isEmpty());
	}

	public void clean() {
		globalMessages = new ArrayList<String>();
		fieldMessages = new HashMap<String, String>();
	}

	@Override
	public String getMessage() {
		String str = "";
		if (globalMessages != null && !globalMessages.isEmpty()) {
			for (String s : globalMessages) {
				if (str.isEmpty()) {
					str += s;
				} else {
					str += ", " + s;
				}
			}
		}
		if (fieldMessages != null && !fieldMessages.isEmpty()) {
			for (String s : fieldMessages.values()) {
				if (str.isEmpty()) {
					str += s;
				} else {
					str += ", " + s;
				}
			}
		}
		return str;
	}

}
