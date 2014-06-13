package org.mailfilter.ui.view;

import java.util.Collection;

import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.ui.portlet.MailfilterPortlet;
 
@ComponentConfig(
        template =  "app:/templates/mailfilter/webui/UITest.gtmpl",
        events = {
                @EventConfig(listeners = UITest.FinishLessonActionListener.class),
                @EventConfig(listeners = UITest.NextLessonActionListener.class),
                @EventConfig(listeners = UITest.PreviousLessionActionListener.class)

        }
)
public class UITest extends UIContainer {
  Collection<Spammer> list;

  public UITest() {
    DataStorage service = MailfilterPortlet.getDataService();
    try {
      list = service.listSpamer();
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
