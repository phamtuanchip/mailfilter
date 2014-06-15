
package org.mailfilter.ui.form;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.ItemExistsException;

import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.core.model.SelectItemOption;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.Event.Phase;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormSelectBox;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.UIFormTextAreaInput;
import org.exoplatform.webui.form.validator.MandatoryValidator;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.ui.core.DomainNameValidator;
import org.mailfilter.ui.popup.UIPopupComponent;
import org.mailfilter.ui.portlet.MailfilterPortlet;
import org.mailfilter.ui.view.UIContentViewer;
import org.mailfilter.ui.view.UITest;
@ComponentConfig(
		lifecycle = UIFormLifecycle.class,
		template = "system:/groovy/webui/form/UIForm.gtmpl",
		events = {
			@EventConfig(listeners = UIAddForm.SaveActionListener.class),
			@EventConfig(listeners = UIAddForm.UpdateActionListener.class),
			@EventConfig(listeners = UIAddForm.OnchangeActionListener.class, phase = Phase.DECODE),
			@EventConfig(listeners = UIAddForm.CancelActionListener.class, phase = Phase.DECODE)
		}
		)
public class UIAddForm extends UIForm implements UIPopupComponent{
	
	
	final private String[] DEFAULT_ACTIONS = {"Save","Cancel"};
	final private String[] EDIT_ACTIONS = {"Update","Cancel"};
	private boolean isEdit = false;
	public String spammerId = null;
	
	public UIAddForm() throws Exception {
		addChild(new UIFormStringInput("domain", "domain", "").addValidator(MandatoryValidator.class));
		addChild(new UIFormTextAreaInput("email", "email", "")) ;
		List<SelectItemOption<String>> types = new ArrayList<SelectItemOption<String>>() ;
		for(String stt : Spammer.STT){
			types.add(new SelectItemOption<String>(stt,stt)) ;
		}
		UIFormSelectBox type =  new UIFormSelectBox("status", "status", types) ;
		type.setOnChange("Onchange") ;
		addChild(type);
		addChild(new UIFormTextAreaInput("description", "description", ""));
	}

	@Override
	public void activate() throws Exception {

	}

	@Override
	public void deActivate() throws Exception {

	}

	@Override
	public String[] getActions() {
		if(isEdit) return EDIT_ACTIONS;
		else return DEFAULT_ACTIONS;
	}

	static  public class SaveActionListener extends EventListener<UIAddForm> {
		@Override
		public void execute(Event<UIAddForm> event) throws Exception {
			UIAddForm uiForm = event.getSource() ;
			String domain = uiForm.getUIStringInput("domain").getValue();
			if(!new DomainNameValidator().validate(domain)) {
				MailfilterPortlet.showMessage("UIAddForm.msg.domain-invalid", ApplicationMessage.ERROR, null);
				return;
			}
			Spammer es = new Spammer();
			es.setEmail( 
					uiForm.getUIFormTextAreaInput("email").getValue());
			es.setStatus(uiForm.getUIFormSelectBox("status").getValue());
			es.setDescription(uiForm.getUIFormTextAreaInput("description").getValue());
			es.setSender(domain);
			try {
			MailfilterPortlet.getDataService().addSpammer(es);
			} catch (ItemExistsException ie) {
				MailfilterPortlet.showMessage("UIAddForm.msg.item-exist", ApplicationMessage.WARNING, null);
				return;
			}
			MailfilterPortlet portlet = uiForm.getAncestorOfType(MailfilterPortlet.class) ;
			portlet.closePopup();
			UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
		      UITest list = view.getChild(UITest.class);
		     list.setStatus(Spammer.ST_DEFAULT);
		     list.refresh();
			event.getRequestContext().addUIComponentToUpdateByAjax(portlet);

		}
	}
	static  public class UpdateActionListener extends EventListener<UIAddForm> {
		@Override
		public void execute(Event<UIAddForm> event) throws Exception {
			UIAddForm uiForm = event.getSource() ;
			Spammer es = MailfilterPortlet.getDataService().getSpammerById(uiForm.spammerId);
			es.setEmail( 
					uiForm.getUIStringInput("email").getValue());
			es.setStatus(uiForm.getUIFormSelectBox("status").getValue());
			es.setDescription(uiForm.getUIFormTextAreaInput("description").getValue());
			es.setSender(uiForm.getUIStringInput("domain").getValue());
			MailfilterPortlet.getDataService().updateSpammer(es);
			MailfilterPortlet portlet = uiForm.getAncestorOfType(MailfilterPortlet.class) ;
			portlet.closePopup();
			UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
		      UITest list = view.getChild(UITest.class);
		     list.setStatus(Spammer.ST_DEFAULT);
		     list.refresh();
			event.getRequestContext().addUIComponentToUpdateByAjax(portlet);

		}
	}
	
	static  public class OnchangeActionListener extends EventListener<UIAddForm> {
		@Override
		public void execute(Event<UIAddForm> event) throws Exception {
			UIAddForm uiForm = event.getSource() ;
		}
	}
	static  public class CancelActionListener extends EventListener<UIAddForm> {
		@Override
		public void execute(Event<UIAddForm> event) throws Exception {
			UIAddForm uiForm = event.getSource() ;
			MailfilterPortlet calendarPortlet = uiForm.getAncestorOfType(MailfilterPortlet.class) ;
			calendarPortlet.closePopup();
		}
	}
	public void init(Spammer s) {
		this.spammerId = s.getId();
		getUIFormTextAreaInput("email").setValue(s.getEmail());
		getUIStringInput("domain").setValue(s.getSender());
		getUIFormTextAreaInput("description").setValue(s.getDescription());
		getUIFormSelectBox("status").setValue(s.getStatus());
		this.isEdit = true;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
}
