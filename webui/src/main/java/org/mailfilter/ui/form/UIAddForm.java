
package org.mailfilter.ui.form;

import java.util.ArrayList;
import java.util.List;

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
import org.exoplatform.webui.form.validator.EmailAddressValidator;
import org.exoplatform.webui.form.validator.MandatoryValidator;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.ui.popup.UIPopupComponent;
import org.mailfilter.ui.portlet.MailfilterPortlet;
import org.mailfilter.ui.view.UIDataList;
@ComponentConfig(
		lifecycle = UIFormLifecycle.class,
		template = "system:/groovy/webui/form/UIForm.gtmpl",
		events = {
			@EventConfig(listeners = UIAddForm.SaveActionListener.class),
			@EventConfig(listeners = UIAddForm.OnchangeActionListener.class, phase = Phase.DECODE),
			@EventConfig(listeners = UIAddForm.CancelActionListener.class, phase = Phase.DECODE)
		}
		)
public class UIAddForm extends UIForm implements UIPopupComponent{
	
	public UIAddForm() throws Exception {
		addChild(new UIFormStringInput("email", "email", "").addValidator(EmailAddressValidator.class).addValidator(MandatoryValidator.class)) ;
		addChild(new UIFormStringInput("domain", "domain", ""));
		List<SelectItemOption<String>> types = new ArrayList<SelectItemOption<String>>() ;
	    types.add(new SelectItemOption<String>("Select status", "0")) ;
	    types.add(new SelectItemOption<String>("block","1"));
	    types.add(new SelectItemOption<String>("pending","2"));
	    types.add(new SelectItemOption<String>("archive","3"));
	    UIFormSelectBox type =  new UIFormSelectBox("status", "status", types) ;
	    type.setOnChange("Onchange") ;
	    addChild(type);
	    addChild(new UIFormTextAreaInput("description", "description", ""));
	}

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deActivate() throws Exception {
		// TODO Auto-generated method stub

	}
	
	@Override
	  public String[] getActions() {
	    return new String[]{"Save","Cancel"} ;
	  }
	
	static  public class SaveActionListener extends EventListener<UIAddForm> {
		@Override
		public void execute(Event<UIAddForm> event) throws Exception {
			UIAddForm uiForm = event.getSource() ;
			Spammer es = new Spammer();
			es.setEmail( 
			uiForm.getUIStringInput("email").getValue());
			es.setStatus(uiForm.getUIFormSelectBox("status").getValue());
			es.setDescription(uiForm.getUIStringInput("description").getValue());
			es.setSender(uiForm.getUIStringInput("domain").getValue());
			MailfilterPortlet.getDataService().addSpammer(es);
			MailfilterPortlet calendarPortlet = uiForm.getAncestorOfType(MailfilterPortlet.class) ;
			calendarPortlet.closePopup();
			event.getRequestContext().addUIComponentToUpdateByAjax(
			uiForm.getAncestorOfType(MailfilterPortlet.class).findFirstComponentOfType(UIDataList.class));
			
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
}
