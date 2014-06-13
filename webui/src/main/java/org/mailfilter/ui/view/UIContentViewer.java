package org.mailfilter.ui.view;

import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.mailfilter.service.model.Attachment;
import org.mailfilter.service.model.ESession;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.ui.form.UILessonForm;
import org.mailfilter.ui.popup.UIPopupContainer;
import org.mailfilter.ui.portlet.MailfilterPortlet;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tuanp
 * Date: 10/22/13
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
@ComponentConfig(
        template =  "app:/templates/mailfilter/webui/UIContentViewer.gtmpl",
        events = {
                @EventConfig(listeners = UIContentViewer.AddLessonActionListener.class)

        }
)

public class UIContentViewer extends UIContainer {
  Collection<ESession> list;
  Collection<Attachment> mediaList = new ArrayList<Attachment>() ;
  public UIContentViewer() throws Exception {
    addChild(UITest.class, null, null).setRendered(false) ;
    addChild(UIResourceViewer.class, null, null).setRendered(false) ;
    refresh();
  }

  public void refresh(){
    DataStorage service = MailfilterPortlet.getDataService();
    try {
      list = service.getSessions();
      mediaList.addAll(service.getMedias());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  static public class AddLessonActionListener extends EventListener<UIContentViewer> {
    @Override
    public void execute(Event<UIContentViewer> event) throws Exception {
      UIContentViewer view = event.getSource() ;
      MailfilterPortlet portlet = view.getAncestorOfType(MailfilterPortlet.class);
      //UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      //UILessionForm uiLessionForm = uiPopupContainer.addChild(UILessionForm.class, null, null) ;
      //portlet.addPopup(uiLessionForm, 600, 311);
    }
  }
}
