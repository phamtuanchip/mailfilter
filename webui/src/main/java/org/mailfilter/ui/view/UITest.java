package org.mailfilter.ui.view;

import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.mailfilter.service.model.ESession;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.ui.portlet.MailfilterPortlet;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tuanp
 * Date: 10/22/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
@ComponentConfig(
        template =  "app:/templates/mailfilter/webui/UITest.gtmpl",
        events = {
                @EventConfig(listeners = UITest.FinishLessonActionListener.class),
                @EventConfig(listeners = UITest.NextLessonActionListener.class),
                @EventConfig(listeners = UITest.PreviousLessionActionListener.class)

        }
)
public class UITest extends UIContainer {
  Collection<ESession> list;

  public UITest() {
    DataStorage service = MailfilterPortlet.getDataService();
    try {
      list = service.getSessions();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static public class FinishLessonActionListener extends EventListener<UITest> {
    @Override
    public void execute(Event<UITest> event) throws Exception {
      UITest testview = event.getSource() ;
      MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
      //UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      //UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
      //portlet.addPopup(uiLessionForm, 600, 311);
    }
  }

  static public class NextLessonActionListener extends EventListener<UITest> {
    @Override
    public void execute(Event<UITest> event) throws Exception {
      UITest testview = event.getSource() ;
      MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
      //UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      //UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
      //portlet.addPopup(uiLessionForm, 600, 311);
    }
  }

  static public class PreviousLessionActionListener extends EventListener<UITest> {
    @Override
    public void execute(Event<UITest> event) throws Exception {
      UITest testview = event.getSource() ;
      MailfilterPortlet portlet = testview.getAncestorOfType(MailfilterPortlet.class);
      //UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      //UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
      //portlet.addPopup(uiLessionForm, 600, 311);
    }
  }
}
