
package org.mailfilter.ui.view;

import java.util.ArrayList;
import java.util.Collection;

import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.ui.form.UIAddForm;
import org.mailfilter.ui.popup.UIPopupContainer;
import org.mailfilter.ui.portlet.MailfilterPortlet;

@ComponentConfig(
                 template =  "app:/templates/mailfilter/webui/UIDataList.gtmpl", 
                 events = {
                     @EventConfig(listeners = UIDataList.AddSpammerActionListener.class),
                     @EventConfig(listeners = UIDataList.ListAllActionListener.class),
                     @EventConfig(listeners = UIDataList.ListArchiveActionListener.class),
                     @EventConfig(listeners = UIDataList.ListBlockedActionListener.class),
                     @EventConfig(listeners = UIDataList.ListPendingActionListener.class),
                     @EventConfig(listeners = UIDataList.SearchActionListener.class)
                 }
    )

public class UIDataList extends UIContainer {
  private Collection<Spammer> list = new ArrayList<Spammer>();

  public UIDataList() {
    
  }

  public Collection<Spammer> getList() throws Exception {
	return  list;
}

public void setList(Collection<Spammer> list) {
	this.list = list;
}

static public class AddSpammerActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIPopupContainer uiPopupContainer = portlet.createUIComponent(UIPopupContainer.class, null, "formcontainer") ;
      UIAddForm uiForm = uiPopupContainer.addChild(UIAddForm.class, null, null) ;
      portlet.addPopup(uiForm, 400, 270);
    }
  }

static public class SearchActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      String email = event.getRequestContext().getRequestParameter(OBJECTID); 
      listview.setList(MailfilterPortlet.getDataService().searchSpammerByEmail(email)) ;
      event.getRequestContext().addUIComponentToUpdateByAjax(listview);
    }
  }	

  static public class ListAllActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
      UITest list = view.getChild(UITest.class);
      list.setStatus(Spammer.ST_DEFAULT);
      list.refresh();
      event.getRequestContext().addUIComponentToUpdateByAjax(list);
    }
  }
  static public class ListArchiveActionListener extends EventListener<UIDataList> {
	    @Override
	    public void execute(Event<UIDataList> event) throws Exception {
	      UIDataList listview = event.getSource() ;
	      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
	      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
	      UITest list = view.getChild(UITest.class);
	      list.setStatus(Spammer.ST_ARCHIVE);
	      list.refresh();
	      event.getRequestContext().addUIComponentToUpdateByAjax(list);
	    }
	  }

  static public class ListBlockedActionListener extends EventListener<UIDataList> {
    @Override
    public void execute(Event<UIDataList> event) throws Exception {
      UIDataList listview = event.getSource() ;
      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
      UITest list = view.getChild(UITest.class);
      list.setStatus(Spammer.ST_BLOCK);
      list.refresh();
      event.getRequestContext().addUIComponentToUpdateByAjax(list);
    }
  }
  
  static public class ListPendingActionListener extends EventListener<UIDataList> {
	    @Override
	    public void execute(Event<UIDataList> event) throws Exception {
	      UIDataList listview = event.getSource() ;
	      MailfilterPortlet portlet = listview.getAncestorOfType(MailfilterPortlet.class);
	      UIContentViewer view = portlet.findFirstComponentOfType(UIContentViewer.class) ;
	      UITest list = view.getChild(UITest.class);
	      list.setStatus(Spammer.ST_PENDING);
	      list.refresh();
	      event.getRequestContext().addUIComponentToUpdateByAjax(list);
	    }
	  }
}
