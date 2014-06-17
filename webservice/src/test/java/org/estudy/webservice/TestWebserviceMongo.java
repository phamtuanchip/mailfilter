 
package org.estudy.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.exoplatform.common.http.HTTPMethods;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.services.rest.impl.ContainerResponse;
import org.exoplatform.services.rest.impl.MultivaluedMapImpl;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.rest.tools.ByteArrayContainerResponseWriter;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.MembershipEntry;
import org.exoplatform.ws.frameworks.json.impl.JsonGeneratorImpl;
import org.exoplatform.ws.frameworks.json.value.JsonValue;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.JcrDataStorage;
import org.mailfilter.service.storage.impl.MongoDataStorage;
import org.mailfilter.webservice.MailfilterWebservice;

 
public class TestWebserviceMongo extends AbstractResourceTest {

	org.mailfilter.service.storage.DataStorage storage_;
	MailfilterWebservice webservice;
	private Collection<MembershipEntry> membershipEntries = new ArrayList<MembershipEntry>();

	static final String             baseURI = "";
	String username = "root";

	MultivaluedMap<String, String> h = new MultivaluedMapImpl();


	public void setUp() throws Exception {
		RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
		super.setUp();
		webservice = (MailfilterWebservice) container.getComponentInstanceOfType(MailfilterWebservice.class);
		storage_ = (DataStorage) container.getComponentInstanceOfType(MongoDataStorage.class);
		binder.addResource(webservice, null);
		login(username) ;
		h.putSingle("username", username);
	}

	public void tearDown() throws Exception {
		super.tearDown();
		for(Spammer s : storage_.listSpammer()) storage_.removeSpammer(s);	 
		 

	}

	public void testSearchSpammerByEmail() throws Exception {
		Spammer s = new Spammer();
		s.setEmail("phamtuanchip@gmail.com");
		s.setSender("gmail.com");
		storage_.addSpammer(s);
		s = new Spammer();
		s.setEmail("phamtuanchip@hotmail.com");
		s.setSender("hotmail.com");
		
		String extURI = "/mailfilter/api/mg/search/someone@hotmail.com" ; 

		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

		ContainerResponse response = service(HTTPMethods.GET, extURI, baseURI, h, null, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.OK, response.getStatus());
		Collection<Spammer> in = (Collection<Spammer>) response.getEntity();
		assertNotNull(in);
	}

	 
	public void testCheckPermission() throws Exception {


		//assert false ;
		Spammer test = new Spammer();
		test.setEmail("phamtuanchip@gmail.com");
		test.setDescription("this is final test");


		String extURI = "/mailfilter/api/checkPermission/";

		JsonGeneratorImpl generatorImpl = new JsonGeneratorImpl();
		JsonValue json = generatorImpl.createJsonObject(ConversationState.getCurrent().getIdentity().getUserId());
		byte[] data = json.toString().getBytes("UTF-8");

		h.putSingle("content-type", "application/json");
		h.putSingle("content-length", "" + data.length);

		ByteArrayContainerResponseWriter writer = new ByteArrayContainerResponseWriter();

		ContainerResponse response = service(HTTPMethods.POST, extURI, baseURI, h, data, writer);
		assertNotNull(response);
		assertEquals(HTTPStatus.ACCEPTED, response.getStatus());


	}


	private void login(String username) {

		setMembershipEntry("/platform/users", "member", true);
		Identity identity = new Identity(username, membershipEntries);
		ConversationState state = new ConversationState(identity);
		ConversationState.setCurrent(state);
	}

	private void setMembershipEntry(String group, String membershipType, boolean isNew) {
		MembershipEntry membershipEntry = new MembershipEntry(group, membershipType);
		if (isNew) {
			membershipEntries.clear();
		}
		membershipEntries.add(membershipEntry);
	}


	public static String buildResponse(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String message ;
		httpGet.setHeader("Authorization", "Basic " + "root:gtn");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			switch (statusCode) {
			case 404:
				//Log.e(Home.class.toString(), "Service not found: " + statusCode);
				message = "Service not found: " + statusCode;
				break;
			case 401:
				// Log.e(Home.class.toString(), "Need to login: " + statusCode);
				message = "Need to login: " + statusCode;
				break;
			case 200:
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				break;
			default:
				//Log.e(Home.class.toString(), "Error : " + statusCode);
				message = "Error: " + statusCode;
				break;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}


}
