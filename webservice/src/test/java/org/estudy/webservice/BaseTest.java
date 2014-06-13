  

package org.estudy.webservice;

import org.exoplatform.commons.chromattic.ChromatticManager;
import org.exoplatform.component.test.AbstractKernelTest;
import org.exoplatform.component.test.ConfigurationUnit;
import org.exoplatform.component.test.ConfiguredBy;
import org.exoplatform.component.test.ContainerScope;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.component.ComponentRequestLifecycle;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.rest.impl.ApplicationContextImpl;
import org.exoplatform.services.rest.impl.ProviderBinder;
import org.exoplatform.services.rest.impl.RequestHandlerImpl;
import org.exoplatform.services.rest.impl.ResourceBinder;
 
@ConfiguredBy({
	@ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/exo.portal.component.portal-configuration.xml"),
	  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/exo.portal.component.test.jcr-configuration.xml"),
	  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/exo.portal.component.identity-configuration.xml"),
	  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/test-portal-configuration.xml"),
	  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/mailfilter.component.core.test.configuration.xml"),
	  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/mailfilter.test.jcr-configuration.xml"),
	  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/portal/mailfilter.test.portal-configuration.xml"),
  @ConfigurationUnit(scope = ContainerScope.PORTAL, path = "conf/mailfilter.webservice.test-configuration.xml")
})
public abstract class BaseTest extends AbstractKernelTest {

  protected PortalContainer container;

  protected ProviderBinder providers;

  protected ResourceBinder     binder;

  protected RequestHandlerImpl requestHandler;

  protected OrganizationService  orgService;

  protected ChromatticManager chromatticManager;

  public void setUp() throws Exception {
    container = PortalContainer.getInstance();
    chromatticManager = (ChromatticManager)container.getComponentInstanceOfType(ChromatticManager.class);
    orgService = (OrganizationService) container.getComponentInstanceOfType(OrganizationService.class);
    binder = (ResourceBinder) container.getComponentInstanceOfType(ResourceBinder.class);
    requestHandler = (RequestHandlerImpl) container.getComponentInstanceOfType(RequestHandlerImpl.class);
    ProviderBinder.setInstance(new ProviderBinder());
    providers = ProviderBinder.getInstance();
    ApplicationContextImpl.setCurrent(new ApplicationContextImpl(null, null, providers));
    binder.clear();
  }

  protected void start() {
    ((ComponentRequestLifecycle)orgService).startRequest(container);
  }

  protected void stop() {
    ((ComponentRequestLifecycle)orgService).endRequest(container);
  }  

  public void tearDown() throws Exception {
  }
}
