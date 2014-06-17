
package org.mailfilter.webservice;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.impl.JcrDataStorage;
import org.mailfilter.service.storage.impl.MongoDataStorage;

//localhost:8080/rest/mailfilter/api/
@Path("/mailfilter/api")
public class MailfilterWebservice implements ResourceContainer{
	public final static String PRIVATE = "/private";
	public final static String BASE_URL = "/mailfilter/api";
	final public static String BASE_URL_PRIVATE = PRIVATE + BASE_URL + "/";
	private Log log = ExoLogger.getExoLogger("mailfilter.webservice");
	/** The Constant LAST_MODIFIED_PROPERTY. */
	private static final String LAST_MODIFIED_PROPERTY = "Last-Modified";

	/** The Constant IF_MODIFIED_SINCE_DATE_FORMAT. */
	private static final String IF_MODIFIED_SINCE_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	static CacheControl cc = new CacheControl();
	static {
		cc.setNoCache(true);
		cc.setNoStore(true);
	}

	private DataStorage storage_ = null;
	private DataStorage storage_mg = null;
	private Object getJcrDataStorage() {
		storage_ = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(JcrDataStorage.class);
		if(storage_ == null){
			return Response.status(HTTPStatus.UNAVAILABLE).cacheControl(cc).build();
		}
		return storage_;
	}
	
	private Object getJcrDataStorageMg() {
		storage_mg = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(MongoDataStorage.class);
		if(storage_mg == null){
			return Response.status(HTTPStatus.UNAVAILABLE).cacheControl(cc).build();
		}
		return storage_mg;
	}

	public MailfilterWebservice() {
		storage_ = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(JcrDataStorage.class);
		
		storage_mg = (DataStorage)ExoContainerContext.getCurrentContainer()
				.getComponentInstanceOfType(MongoDataStorage.class);
	}


	@POST
	@RolesAllowed("users")
	@Path("/checkPermission/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkPermission(String currentIdentity) throws Exception {
		try {
			Identity instance = ConversationState.getCurrent().getIdentity();
			if(instance == null || !currentIdentity.equals(instance.getUserId())) Response.status(HTTPStatus.UNAUTHORIZED).cacheControl(cc).build();
			return Response.status(HTTPStatus.ACCEPTED).cacheControl(cc).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(HTTPStatus.INTERNAL_ERROR).cacheControl(cc).build();
		}


	}

	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search/{email}")
	public Response searchDomain(@PathParam("email") String email) throws Exception {
		try {
			Collection<Spammer> list = storage_.searchSpammerByEmail(email);
			return Response.ok(list, MediaType.APPLICATION_JSON).cacheControl(cc).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(HTTPStatus.INTERNAL_ERROR).cacheControl(cc).build();
		}

	}
	
	@GET
	//@RolesAllowed("users")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mg/search/{email}")
	public Response searchDomainFromMongo(@PathParam("email") String email) throws Exception {
		try {
			Collection<Spammer> list = storage_mg.searchSpammerByEmail(email);
			return Response.ok(list, MediaType.APPLICATION_JSON).cacheControl(cc).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(HTTPStatus.INTERNAL_ERROR).cacheControl(cc).build();
		}

	}



}
