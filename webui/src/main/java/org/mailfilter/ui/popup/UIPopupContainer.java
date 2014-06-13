 
package org.mailfilter.ui.popup;

import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIContainerLifecycle;
 
@ComponentConfig(lifecycle = UIContainerLifecycle.class)
public class UIPopupContainer extends UIContainer implements UIPopupComponent {

  public static String UITASKPOPUP = "UIPopupAddTaskContainer".intern() ;
  public static String UIEVENTPOPUP = "UIPopupAddEventContainer".intern();
  public static String UICALENDARPOPUP = "uiPopupAddCalendarContainer".intern();
  public static String UICALENDAR_SETTING_POPUP = "UIPopupCalendarSettingContainer".intern();
  public UIPopupContainer() throws Exception {
    UIPopupAction uiPopupAction = addChild(UIPopupAction.class, null, "UICalendarChildPopup");
    uiPopupAction.getChild(UIPopupWindow.class).setId("UICalendarChildPopupWindow") ;
    
  }
  @Override
  public void activate() throws Exception {
  }

  @Override
  public void deActivate() throws Exception {
    UIPopupAction uiPopupAction = getChild(UIPopupAction.class) ;
    uiPopupAction.deActivate() ;
    WebuiRequestContext context = RequestContext.getCurrentInstance() ;
    context.addUIComponentToUpdateByAjax(uiPopupAction) ;
  }
}
