
package org.mailfilter.test;

import java.util.ArrayList;
import java.util.Collection;

import javax.jcr.ItemExistsException;

import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.MongoDataStorage;



public class MongoMailfilterServiceTest extends BaseServiceTestCase {

	private RepositoryService repositoryService_ ;
	private DataStorage  storage_;
	private static String   username = "root";
	public Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();
	private OrganizationService organizationService_;

	public void setUp() throws Exception {
		super.setUp();
		repositoryService_ = getService(RepositoryService.class);
		organizationService_ = (OrganizationService) getService(OrganizationService.class);
		storage_ = getService(MongoDataStorage.class);
	}
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		for(Spammer s : storage_.listSpammer()) storage_.removeSpammer(s);	 
	}

	private void loginUser(String userId) {
		Identity identity = new Identity(userId, membershipEntries);
		ConversationState state = new ConversationState(identity);
		ConversationState.setCurrent(state);
	}
	//mvn test -Dtest=MongoMailfilterServiceTest#testInitServices
	public void testInitServices() throws Exception{

		assertNotNull(repositoryService_) ;
		assertEquals(repositoryService_.getDefaultRepository().getConfiguration().getName(), "repository");
		assertEquals(repositoryService_.getDefaultRepository().getConfiguration().getDefaultWorkspaceName(), "portal-test");
		assertNotNull(organizationService_) ;

		//assertEquals(organizationService_.getUserHandler().findAllUsers().getSize(), 8);

		assertNotNull(storage_);

	}


	private Spammer createSpamer(String email, String sender, String status, String des) {
		Spammer s = new Spammer();
		s.setEmail(email);
		s.setDescription(des);
		s.setSender(sender);
		s.setStatus(status);
		return s;
	}



	//mvn test -Dtest=MongoMailfilterServiceTest#testAddSpamer
	public void testAddSpamer() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));
	}
	//mvn test -Dtest=MongoMailfilterServiceTest#testRemoveSpamer
		public void testRemoveSpamer() throws Exception {
			Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
			assertNotNull(storage_.addSpammer(s));
			assertEquals(1, storage_.listSpammer().size());
			storage_.removeSpammer(s);
			assertEquals(0, storage_.listSpammer().size());
	}

	//mvn test -Dtest=MongoMailfilterServiceTest#testAddSpamerIfExist
	public void testAddSpamerIfExist() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "gmail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));
		s = createSpamer("phamtuanchip@mail.com", "gmail.com", "1", "description");
		try {
			storage_.addSpammer(s);
		} catch (ItemExistsException ie) {
			assertTrue(true);
			return;
		}
		assertFalse(false);


	}

	/*

	//mvn test -Dtest=MongoMailfilterServiceTest#testDefaultInitliliazeSpamer
	public void testDefaultInitliliazeSpamer() throws Exception {
		if(storage_.listSpammer().size()>0)
			assertEquals(5, storage_.listSpammer().size());
	}



	//mvn test -Dtest=MongoMailfilterServiceTest#testGetSpamerById
	public void testGetSpamerById() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));

		assertNotNull(storage_.getSpammerById(s.getId()));
	}

	//mvn test -Dtest=MongoMailfilterServiceTest#testUpdateSpammer
	public void testUpdateSpammer() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));
		s.setEmail("phamtuanchip@yahoo.com");
		storage_.updateSpammer(s);
		assertEquals("phamtuanchip@yahoo.com", storage_.getSpammerById(s.getId()).getEmail());

	}


	//mvn test -Dtest=MongoMailfilterServiceTest#testListSpamer
	public void testListSpamer() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));
		assertEquals(1, storage_.listSpammer().size());
	}

	

	//mvn test -Dtest=MongoMailfilterServiceTest#testListSpammerByStatus
	public void testListSpammerByStatus() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));
		assertEquals(1, storage_.listSpammerByStatus("1").size());

		s = createSpamer("phamtuanchip@mail.com", "gmail.com", "2", "description");
		assertNotNull(storage_.addSpammer(s));
		assertEquals(1, storage_.listSpammerByStatus("2").size());

		s = createSpamer("phamtuanchip@mail.com", "hotmail.com", "3", "description");
		assertNotNull(storage_.addSpammer(s));
		assertEquals(1, storage_.listSpammerByStatus("3").size());

		assertEquals(3, storage_.listSpammer().size());

	}

	//mvn test -Dtest=MongoMailfilterServiceTest#testSearchSpammerByEmail
	public void testSearchSpammerByEmail() throws Exception {
		Spammer s = createSpamer("phamtuanchip@mail.com", "mail.com", "1", "description");
		assertNotNull(storage_.addSpammer(s));

		s = createSpamer("phamtuanchip@mail.com", "gmail.com", "2", "description");
		assertNotNull(storage_.addSpammer(s));


		s = createSpamer("phamtuanchip@mail.com", "hotmail.com", "3", "description");
		assertNotNull(storage_.addSpammer(s));


		assertEquals(3, storage_.listSpammer().size());

		assertEquals(1, storage_.searchSpammerByEmail("phamtuanchip@hotmail.com").size());

	}
	 */
}
