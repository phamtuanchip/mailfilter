
package org.mailfilter.ui.portlet;

import javax.portlet.PortletPreferences;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.JcrDataStorage;
import org.mailfilter.service.storage.impl.MongoDataStorage;
import org.mailfilter.ui.popup.UIPopupAction;
import org.mailfilter.ui.view.UIContentViewer;
import org.mailfilter.ui.view.UIDataList;


@ComponentConfig(
		lifecycle = UIApplicationLifecycle.class,
		template  = "app:/templates/mailfilter/webui/MailfilterPortlet.gtmpl"
		)
public class MailfilterPortlet extends UIPortletApplication 
{
	public static Log log = ExoLogger.getExoLogger(MailfilterPortlet.class);

	public MailfilterPortlet() throws Exception 
	{
		addChild(UIDataList.class, null, null) ;
		addChild(UIContentViewer.class, null, null) ;
		UIPopupAction uiPopup =  addChild(UIPopupAction.class, null, null) ;
		uiPopup.setId("UIEPopupAction") ;
		uiPopup.getChild(UIPopupWindow.class).setId("UIEPopupWindow") ;
	}


	public void addPopup(UIComponent type, int width, int height) throws Exception{
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		UIPopupAction popupAction = getChild(UIPopupAction.class);
		popupAction.deActivate();
		popupAction.activate(type, width, height);
		context.addUIComponentToUpdateByAjax(popupAction) ;
	}
	public void closePopup()throws Exception{
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		UIPopupAction popupAction = getChild(UIPopupAction.class) ;
		popupAction.deActivate() ;
		context.addUIComponentToUpdateByAjax(popupAction) ;

	}
	public static DataStorage getDataService() {
		PortletRequestContext pcontext = (PortletRequestContext) RequestContext.getCurrentInstance();
		PortletPreferences portletPref = pcontext.getRequest().getPreferences();
		Boolean isMongo = Boolean.parseBoolean(portletPref.getValue("usingMongoDB", "false").trim());
		if(isMongo) return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(MongoDataStorage.class);
		return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
	}

	public static void showMessage(String message, int messageType, Object params []){
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		context.getUIApplication()
		.addMessage(new ApplicationMessage(message, params, messageType));
	}

}
