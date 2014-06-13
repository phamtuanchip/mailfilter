 
package org.mailfilter.test;

import java.util.ArrayList;
import java.util.Collection;

import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.JcrDataStorage;


 
public class MailfilterServiceTest extends BaseServiceTestCase {

  private RepositoryService repositoryService_ ;
  private DataStorage  storage_;
  private static String   username = "root";
  public Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();
  private OrganizationService organizationService_;

  public void setUp() throws Exception {
    super.setUp();
    repositoryService_ = getService(RepositoryService.class);
    organizationService_ = (OrganizationService) getService(OrganizationService.class);
    storage_ = getService(JcrDataStorage.class);
  }
  @Override
  public void tearDown() throws Exception {
	  super.tearDown();
	 for(Spammer s : storage_.listSpamer()) storage_.removeSpammer(s);	 
  }

  private void loginUser(String userId) {
    Identity identity = new Identity(userId, membershipEntries);
    ConversationState state = new ConversationState(identity);
    ConversationState.setCurrent(state);
  }
  //mvn test -Dtest=MailfilterServiceTest#testInitServices
  public void testInitServices() throws Exception{

    assertNotNull(repositoryService_) ;
    assertEquals(repositoryService_.getDefaultRepository().getConfiguration().getName(), "repository");
    assertEquals(repositoryService_.getDefaultRepository().getConfiguration().getDefaultWorkspaceName(), "portal-test");
    assertNotNull(organizationService_) ;

    //assertEquals(organizationService_.getUserHandler().findAllUsers().getSize(), 8);

    assertNotNull(storage_);

  }
  

  
  //mvn test -Dtest=MailfilterServiceTest#testAddSpamer
  public void testAddSpamer() throws Exception {
	  Spammer s = new Spammer();
	  s.setEmail("phamtuanchip@mail.com");
	  assertNotNull(storage_.addSpammer(s));
  }
  
//mvn test -Dtest=MailfilterServiceTest#testListSpamer
  public void testListSpamer() throws Exception {
	  Spammer s = new Spammer();
	  s.setEmail("phamtuanchip@mail.com");
	  assertNotNull(storage_.addSpammer(s));
	  assertEquals(1, storage_.listSpamer().size());
  }
  
//mvn test -Dtest=MailfilterServiceTest#testRemoveSpamer
  public void testRemoveSpamer() throws Exception {
	  Spammer s = new Spammer();
	  s.setEmail("phamtuanchip@mail.com");
	  assertNotNull(storage_.addSpammer(s));
	  assertEquals(1, storage_.listSpamer().size());
	  storage_.removeSpammer(s);
	  assertEquals(0, storage_.listSpamer().size());
  }
  
}
