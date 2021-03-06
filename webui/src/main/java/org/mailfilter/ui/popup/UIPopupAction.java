 
package org.mailfilter.ui.popup;

import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.Lifecycle;


@ComponentConfig( lifecycle = Lifecycle.class )
public class UIPopupAction extends UIContainer {
	public UIPopupAction() throws Exception {
		addChild(createUIComponent(UIPopupWindow.class, null, null).setRendered(false));
	}

	@Override
	public void processRender(WebuiRequestContext context) throws Exception {
		context.getWriter().append("<span class=\"").append(getId()).append("\" id=\"").append(getId()).append("\">");
		if(!getChildren().isEmpty())renderChildren(context) ;
		context.getWriter().append("</span>");
	}

	public <T extends UIComponent> T activate(Class<T> type, int width) throws Exception {
		return activate(type, null, width, 0);
	}

	public <T extends UIComponent> T activate(Class<T> type, String configId, int width, int height)
			throws Exception {
		T comp = createUIComponent(type, configId, null);
		activate(comp, width, height);
		return comp;
	}

	public void activate(UIComponent uiComponent, int width, int height) throws Exception {
		activate(uiComponent, width, height, true);
	}

	public void activate(UIComponent uiComponent, int width, int height, boolean isResizeable)
			throws Exception {
		UIPopupWindow popup = getChild(UIPopupWindow.class);
		popup.setUIComponent(uiComponent);
		((UIPopupComponent) uiComponent).activate();
		popup.setWindowSize(width, height);
		popup.setRendered(true);
		popup.setShow(true);
		popup.setResizable(isResizeable);
	}

	public void deActivate() throws Exception {
		UIPopupWindow popup = getChild(UIPopupWindow.class);
		if (popup.getUIComponent() != null)
			((UIPopupComponent) popup.getUIComponent()).deActivate();
		popup.setUIComponent(null);
		popup.setRendered(false);
	}

	public void cancelPopupAction() throws Exception {
		deActivate();
		WebuiRequestContext context = RequestContext.getCurrentInstance();
		context.addUIComponentToUpdateByAjax(this);
	}
}