
package org.mailfilter.ui.view;

import java.util.Collection;

import org.exoplatform.portal.webui.container.UIContainer;
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
                 template =  "app:/templates/mailfilter/webui/UIDataList.gtmpl", 
                 events = {
                     @EventConfig(listeners = UIDataList.AddLessonActionListener.class),
                     @EventConfig(listeners = UIDataList.AddQuestionActionListener.class),
                     @EventConfig(listeners = UIDataList.AddMediaActionListener.class),
                     @EventConfig(listeners = UIDataList.TestActionListener.class),
                     @EventConfig(listeners = UIDataList.ResourceActionListener.class)
                 }
    )

public class UIDataList extends UIContainer {
  private Collection<Spammer> list;

  public UIDataList() {
    DataStorage service = MailfilterPortlet.getDataService();
    try {
      list = service.listSpamer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Collection<Spammer> getList() throws Exception {
	return  MailfilterPortlet.getDataService().listSpamer();
}

public void setList(Collection<Spammer> list) {
	this.list = list;
}

static public class AddLessonActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UIAddForm uiForm = uiPopupContainer.addChild(UIAddForm.class, null, null) ;
      portlet.addPopup(uiForm, 800, 600);
    }
  }

  static public class AddQuestionActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
    }
  }

  static public class AddMediaActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
    }
  }

  static public class TestActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
      view.setRenderedChild(UITest.class);
    }
  }
  
  static public class ResourceActionListener extends EventListener<UIDataList> {
	    @Override
	    public void execute(Event<UIDataList> event) throws Exception {
	      UIDataList listview = event.getSource() ;
	      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
	      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
	    }
	  }
}
