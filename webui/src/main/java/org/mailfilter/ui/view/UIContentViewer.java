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
        template =  "app:/templates/mailfilter/webui/UIContentViewer.gtmpl",
        events = {
                @EventConfig(listeners = UIContentViewer.AddLessonActionListener.class)

        }
)

public class UIContentViewer extends UIContainer {
  Collection<Spammer> list;
  public UIContentViewer() throws Exception {
    addChild(UITest.class, null, null) ;
    refresh();
  }

  public void refresh(){
    DataStorage service = MailfilterPortlet.getDataService();
    try {
      list = service.listSpammer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  static public class AddLessonActionListener extends EventListener<UIContentViewer> {
    @Override
    public void execute(Event<UIContentViewer> event) throws Exception {
      UIContentViewer view = event.getSource() ;
      MailfilterPortlet portlet = view.getAncestorOfType(MailfilterPortlet.class);
       
    }
  }
}
