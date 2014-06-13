package org.mailfilter.ui.core;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.JcrDataStorage;

import javax.portlet.PortletPreferences;

 
public class Loader {
  public static final int DEFAULT_VALUE_UPLOAD_PORTAL = -1;
  public static final int DEFAULT_MAX_UPLOAD_FIELD = 10;

  public static DataStorage loadDataService(){
    return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
  }

  public static int getLimitUploadSize() {
    PortletRequestContext pcontext = (PortletRequestContext) RequestContext.getCurrentInstance();
    PortletPreferences portletPref = pcontext.getRequest().getPreferences();
    int limitMB;
    try {
      limitMB = Integer.parseInt(portletPref.getValue("uploadFileSizeLimitMB", "").trim());
    } catch (Exception e) {
      limitMB = DEFAULT_VALUE_UPLOAD_PORTAL;
    }
    return limitMB;
  }
}
