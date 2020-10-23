package br.com.personality.insights.portal.message;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import br.com.personality.insights.exception.AppException;
import br.com.personality.insights.exception.WarningException;

@Dependent
public class MessagesBean implements Serializable {

	private static final long serialVersionUID = -1572910048763046617L;

	public void convert(AppException exception) {

		if (exception.getGlobalMessages() != null
				&& !exception.getGlobalMessages().isEmpty()) {

			for (String msg : exception.getGlobalMessages()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
			}

		}

		if (exception.getFieldMessages() != null
				&& !exception.getFieldMessages().isEmpty()) {

			UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
			for (String msg : exception.getFieldMessages().keySet()) {

				FacesContext.getCurrentInstance().addMessage(
						msg,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, exception
								.getFieldMessages().get(msg), exception
								.getFieldMessages().get(msg)));

				root.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
			        @Override
			        public VisitResult visit(VisitContext context, UIComponent component) {
			            if(component.getId().equals(msg)){
			                UIInput uiInput = (UIInput) component;
			                uiInput.setValid(false);
			                return VisitResult.COMPLETE;
			            }
			            return VisitResult.ACCEPT;              
			        }
			    });

			}

		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

	}

	public void convert(AppException exception, boolean keepMessages) {

		if (exception.getGlobalMessages() != null
				&& !exception.getGlobalMessages().isEmpty()) {

			for (String msg : exception.getGlobalMessages()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
			}

		}

		if (exception.getFieldMessages() != null
				&& !exception.getFieldMessages().isEmpty()) {

			UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
			for (String msg : exception.getFieldMessages().keySet()) {

				FacesContext.getCurrentInstance().addMessage(
						msg,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, exception
								.getFieldMessages().get(msg), exception
								.getFieldMessages().get(msg)));

				root.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
			        @Override
			        public VisitResult visit(VisitContext context, UIComponent component) {
			            if(component.getId().equals(msg)){
			                UIInput uiInput = (UIInput) component;
			                uiInput.setValid(false);
			                return VisitResult.COMPLETE;
			            }
			            return VisitResult.ACCEPT;              
			        }
			    });

			}

		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(keepMessages);

	}

	public void convert(WarningException exception, boolean keepMessage) {

		if (exception.getGlobalMessages() != null
				&& !exception.getGlobalMessages().isEmpty()) {

			for (String msg : exception.getGlobalMessages()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
			}

		}

		if (exception.getFieldMessages() != null
				&& !exception.getFieldMessages().isEmpty()) {

			UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
			for (String msg : exception.getFieldMessages().keySet()) {

				FacesContext.getCurrentInstance().addMessage(
						msg,
						new FacesMessage(FacesMessage.SEVERITY_WARN, exception
								.getFieldMessages().get(msg), exception
								.getFieldMessages().get(msg)));

				root.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
			        @Override
			        public VisitResult visit(VisitContext context, UIComponent component) {
			            if(component.getId().equals(msg)){
			                UIInput uiInput = (UIInput) component;
			                uiInput.setValid(false);
			                return VisitResult.COMPLETE;
			            }
			            return VisitResult.ACCEPT;              
			        }
			    });

			}

		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(keepMessage);

	}

	public void addMessage(FacesMessage.Severity severity, String message) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages");
			message = bundle.getString(message);
		} catch (MissingResourceException e) {
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, message, ""));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}

	public void addMessage(FacesMessage.Severity severity, String field,
			String message) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages");
			message = bundle.getString(message);
		} catch (MissingResourceException e) {
		}
		FacesContext.getCurrentInstance()
				.addMessage(
						field,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, message,
								message));

		if (severity == FacesMessage.SEVERITY_ERROR) {
			UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
			root.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
		        @Override
		        public VisitResult visit(VisitContext context, UIComponent component) {
		            if(component.getId().equals(field)){
		                UIInput uiInput = (UIInput) component;
		                uiInput.setValid(false);
		                return VisitResult.COMPLETE;
		            }
		            return VisitResult.ACCEPT;              
		        }
		    });
		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}

	public String getFromBundle(String message) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("messages");
			String msg = bundle.getString(message);
			return msg;
		} catch (MissingResourceException e) {
			return message;
		}
	}

}
