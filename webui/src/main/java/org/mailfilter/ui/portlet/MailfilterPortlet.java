 
package org.mailfilter.ui.portlet;

import javax.imageio.ImageIO;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;
import org.mailfilter.service.model.Attachment;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.JcrDataStorage;
import org.mailfilter.ui.popup.UIPopupAction;
import org.mailfilter.ui.view.UIContentViewer;
import org.mailfilter.ui.view.UILessonList;


@ComponentConfig(
		lifecycle = UIApplicationLifecycle.class,
		template  = "app:/templates/mailfilter/webui/MailfilterPortlet.gtmpl"
		)
public class MailfilterPortlet extends UIPortletApplication 
{
	public static Log log = ExoLogger.getExoLogger(MailfilterPortlet.class);

	public MailfilterPortlet() throws Exception 
	{
		addChild(UILessonList.class, null, null) ;
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
		return (DataStorage) PortalContainer.getInstance().getComponentInstanceOfType(JcrDataStorage.class);
	}
	
	public static void showMessage(String message, int messageType, Object params []){
		WebuiRequestContext context = RequestContext.getCurrentInstance() ;
		context.getUIApplication()
        .addMessage(new ApplicationMessage(message, params, messageType));
	}
	
	public String getRestThumbnailLinkFor(Attachment attachment, int oneFixedDimension) throws Exception
	  {
	    int[] imageDimension = getScaledImageDimensionFor(attachment, oneFixedDimension);

	    return "/"+PortalContainer.getInstance().getRestContextName()+ "/mailfilter/api/thumbnail/" + imageDimension[0] + "x" + imageDimension[1]
	        + "/repository/portal-system" + attachment.getDataPath();
	  }
	
	/**
	   * scale the image and return dimensions of image given one fixed dimension
	   *
	   * @param imageAttachment
	   * @param fixedDimension
	   * @return an array of new dimensions {width, height}
	   * @throws Exception
	   */
	  private int[] getScaledImageDimensionFor(Attachment imageAttachment, int fixedDimension) throws Exception
	  {
	    int width = getImageAttachmentWidth(imageAttachment);
	    int height = getImageAttachmentHeight(imageAttachment);
	    int biggerDimension = width > height ? width : height;
	    int smallerDimension = biggerDimension == width ? height : width;
	    double scalingRatio = (double) biggerDimension / fixedDimension;
	    int newScaledDimension =  (int) Math.round(smallerDimension / scalingRatio);
	    if (width > height) return new int[] { fixedDimension, newScaledDimension };
	    else if (width == height) return new int[] { fixedDimension, fixedDimension};
	    else return new int[] { newScaledDimension, fixedDimension};
	  }
	  
	  private int getImageAttachmentWidth(Attachment attachment) throws Exception
	  {
	    return ImageIO.read(attachment.getInputStream()).getWidth();
	  }

	  private int getImageAttachmentHeight(Attachment attachment) throws Exception
	  {
	    return ImageIO.read(attachment.getInputStream()).getHeight();
	  }



}
