package org.mailfilter.ui.view;

import java.util.Collection;

import javax.jcr.ItemNotFoundException;

import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.ui.form.UIAddForm;
import org.mailfilter.ui.popup.UIPopupContainer;
import org.mailfilter.ui.portlet.MailfilterPortlet;

@ComponentConfig(
		template =  "app:/templates/mailfilter/webui/UITest.gtmpl",
		events = {
				@EventConfig(listeners = UITest.FinishActionListener.class),
				@EventConfig(listeners = UITest.NextActionListener.class),
				@EventConfig(listeners = UITest.PreviousActionListener.class),
				@EventConfig(listeners = UITest.EditActionListener.class),
				@EventConfig(listeners = UITest.RemoveActionListener.class, confirm="UITest.msg.confirm-delete")

		}
		)
public class UITest extends UIContainer {
	private Collection<Spammer> list;
	private String status = Spammer.ST_DEFAULT;

	public UITest() {
		DataStorage service = MailfilterPortlet.getDataService();
		try {
			list = service.listSpammer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection<Spammer> getList() {
		return list;
	}

	public void setList(Collection<Spammer> list) {
		this.list = list;
	}
	public void refresh(){
		try {
			if(Spammer.ST_DEFAULT.equals(status))
				this.list = MailfilterPortlet.getDataService().listSpammer();
			else
				this.list = MailfilterPortlet.getDataService().listSpammerByStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	static public class FinishActionListener extends EventListener<UITest> {
		@Override
		public void execute(Event<UITest> event) throws Exception {
			UITest testview = event.getSource() ;
			MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
		}
	}

	static public class NextActionListener extends EventListener<UITest> {
		@Override
		public void execute(Event<UITest> event) throws Exception {
			UITest testview = event.getSource() ;
			MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
		}
	}

	static public class PreviousActionListener extends EventListener<UITest> {
		@Override
		public void execute(Event<UITest> event) throws Exception {
			UITest testview = event.getSource() ;
			MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
		}
	}
	static public class EditActionListener extends EventListener<UITest> {
		@Override
		public void execute(Event<UITest> event) throws Exception {
			UITest testview = event.getSource() ;
			MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
			try {
			
			String id = event.getRequestContext().getRequestParameter(OBJECTID); 
			Spammer s = MailfilterPortlet.getDataService().getSpammerById(id);
			UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
			UIAddForm uiForm = uiPopupContainer.addChild(UIAddForm.class, null, null) ;
			uiForm.init(s);
			portlet.addPopup(uiForm, 400, 400);
			}catch (ItemNotFoundException e) {
				portlet.addMessage(new ApplicationMessage("UITest.msg.item-not-found", null));
			}
		}
	}
	static public class RemoveActionListener extends EventListener<UITest> {
		@Override
		public void execute(Event<UITest> event) throws Exception {
			UITest testview = event.getSource() ;
			MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
			String id = event.getRequestContext().getRequestParameter(OBJECTID); 
			Spammer s = new Spammer();
			s.setId(id);
			MailfilterPortlet.getDataService().removeSpammer(s);
			testview.refresh();
			event.getRequestContext().addUIComponentToUpdateByAjax(portlet);
		}
	}
}
