
package org.mailfilter.service.storage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import org.exoplatform.commons.utils.ExoProperties;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.mailfilter.service.Util;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;

public class JcrDataStorage implements DataStorage {
	private NodeHierarchyCreator nodeHierarchyCreator_;
	private RepositoryService repoService_;
	private SessionProviderService sessionProviderService_;
	private static final Log       log                 = ExoLogger.getLogger("JcrDataStorage.class");
	public JcrDataStorage(InitParams params, NodeHierarchyCreator nodeHierarchyCreator, RepositoryService repoService){
		nodeHierarchyCreator_ = nodeHierarchyCreator;
		repoService_ = repoService;
		ExoContainer container = ExoContainerContext.getCurrentContainer();
		sessionProviderService_ = (SessionProviderService) container.getComponentInstanceOfType(SessionProviderService.class);
		if(params != null && params.getPropertiesParam("default.server.bannned") != null){
			ExoProperties props = params.getPropertiesParam("default.server.bannned").getProperties();
			String list = props.getProperty("name");
			try {
				if(list != null && list.split(",").length > 0){
					for (String name : list.split(",") ) {
						Spammer s = new Spammer();
						s.setSender(name);
						s.setEmail("*.*");
						s.setDescription("block andy email from this donmain: " + name);
						s.setStatus(Spammer.ST_BLOCK);
						try {
							addSpammer(s);
						}catch (ItemExistsException e) {
							continue;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("Error init data base " + e.getMessage());
			}
		}
	}





	private Spammer getSpamerProp(Node ses){
		Spammer e = new Spammer();
		try {
			e.setId(ses.getName());
			if(ses.hasProperty(Spammer.P_MAIL)) 
				e.setEmail(ses.getProperty(Spammer.P_MAIL).getString());
			if(ses.hasProperty(Spammer.P_SENDER)) 
				e.setSender(ses.getProperty(Spammer.P_SENDER).getString());
			if(ses.hasProperty(Spammer.P_DESCRIPTION)) 
				e.setDescription(ses.getProperty(Spammer.P_DESCRIPTION).getString());
			if(ses.hasProperty(Spammer.P_STATUS)) 
				e.setStatus(ses.getProperty(Spammer.P_STATUS).getString());
		} catch (Exception e2) {
			log.error("error getSpamerProp " +e2.getMessage());
			return null ;
		}
		return e;
	}


	private boolean isExists(String nt, String proName, String value){
		try {
			QueryManager qm = getStorageHome().getSession().getWorkspace().getQueryManager();
			Query q = qm.createQuery("SELECT "+proName+" FROM " + nt + " WHERE " + proName + " = '" + value +"'", Query.SQL);
			return q.execute().getRows().getSize() != 0;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false ;
		}


	}


	private SessionProvider createSessionProvider() {
		SessionProvider provider = sessionProviderService_.getSessionProvider(null);
		if (provider == null) {
			//log.info("No user session provider was available, trying to use a system session provider");
			provider = sessionProviderService_.getSystemSessionProvider(null);
		}
		return SessionProvider.createSystemProvider();
	}

	private String valuesToString (Value[] values, String regex) throws Exception{
		StringBuffer sb = new StringBuffer();
		for (Value v : values){
			sb.append(regex).append(v.getString());
		}
		return sb.toString().replaceFirst(regex, "");
	}

	private Collection<String> valuesToStrings (Value[] values) throws Exception{
		Collection<String> list = new ArrayList<String>();
		for (Value v : values){
			list.add(v.getString());
		}
		return list;
	}

	private Map<String, Collection<String>> valuesToMap(Value[] values, String regex1, String regex2) throws Exception{
		Map<String, Collection<String>> result = new HashMap<String, Collection<String>>();
		for(Value val:values){
			String value = val.getString();
			String[] arr =  value.split(regex1);
			String k =arr[0];
			String v =arr[1]; 
			result.put(k, Arrays.asList(v.split(regex2)));
		}
		return result;
	}

	private Collection<String> mapToStrings(Map<String, Collection<String>> quest, String regex1, String regex2){
		Collection<String> values = new ArrayList<String>();
		for(String k : quest.keySet()) {
			StringBuffer sb = new StringBuffer(k).append(regex1);
			for(String s : quest.get(k)){
				sb.append(s).append(regex2);
			}
			values.add(sb.toString());
		}
		return values;
	}





	// filter app

	public Node getStorageHome() throws RepositoryException, Exception {
		SessionProvider sProvider = createSessionProvider();
		Node publicApp = sProvider.getSession(repoService_.getCurrentRepository().getConfiguration().getDefaultWorkspaceName(), repoService_.getCurrentRepository()).getRootNode(); 
		try {
			return publicApp.getNode(Util.FILTER_APP);
		} catch (PathNotFoundException e) {
			Node eApp = publicApp.addNode(Util.FILTER_APP, Util.NT_UNSTRUCTURED);
			publicApp.getSession().save();
			return eApp;
		}
	}

	private Node getFilterHome() throws RepositoryException, Exception {
		Node eHome = getStorageHome();
		try {
			return  eHome.getNode(Util.MAIL_FILTER);
		} catch (PathNotFoundException e) {
			eHome.addNode(Util.MAIL_FILTER, Util.NT_UNSTRUCTURED);
			eHome.getSession().save();
			return eHome.getNode(Util.MAIL_FILTER);
		}  


	}


	@Override
	public Spammer getSpammerById(String id) throws Exception {
		try {
			Node sesHome = getFilterHome();
			Spammer s = getSpamerProp(sesHome.getNode(id));
			return s;
		} catch (PathNotFoundException pe) {
			log.info("not found getSpammerById " + pe.getMessage());
			throw new ItemNotFoundException(pe.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("error getSpammerById " + e.getMessage());
			throw e;

		}
	}

	@Override
	public Collection<Spammer> listSpammer() throws Exception {
		Collection<Spammer> list = new ArrayList<Spammer>();
		try {
			Node sesHome = getFilterHome();
			NodeIterator it = sesHome.getNodes();

			while (it.hasNext()) {
				list.add(getSpamerProp(it.nextNode()));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error listSpamer " + e.getMessage());
			throw e;

		}
	}

	@Override
	public void removeSpammer(Spammer s) throws Exception {
		Node sesHome = getFilterHome();
		try {
			Node ses = sesHome.getNode(s.getId());
			ses.remove();
			sesHome.save();
		} catch (PathNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" error removeSpamer " +e.getMessage());
			throw e;
		}

	}

	@Override
	public Spammer updateSpammer(Spammer s) throws Exception {
		Node qHome = getFilterHome();
		Node spamerNode ;
		try {
			spamerNode = setSpamerProp(s, qHome.getNode(s.getId()));
			spamerNode.save();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error updateSpammer " + e.getMessage());
			throw e;
		}

		return s;
	}

	@Override
	public Spammer addSpammer(Spammer s) throws Exception {
		Node qHome = getFilterHome();
		Node spamerNode ;
		if(isExists(Spammer.NT_NAME, Spammer.P_SENDER, s.getSender())) throw new ItemExistsException();
		try {
			spamerNode = setSpamerProp(s, qHome.addNode(s.getId(), Spammer.NT_NAME));
			spamerNode.getSession().save();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error addSpamer " + e.getMessage());
			throw e;
		}

		return s;
	}

	@Override
	public Collection<Spammer> updateSpammers(Collection<Spammer> list)
			throws Exception {

		return null;
	}

	@Override
	public Collection<Spammer> addSpammers(Collection<Spammer> list) throws Exception {
		Node qHome = getStorageHome();
		for (Spammer s : list) {
			try {
				setSpamerProp(s, qHome.addNode(s.getId(), Spammer.NT_NAME));
			} catch (Exception e) {
				log.error("error addSpamers " + e.getMessage());
				throw e;
			}

		}
		qHome.getSession().save();
		return list;
	}
	private Node setSpamerProp(Spammer e, Node tes){
		try {
			tes.setProperty(Spammer.P_MAIL, e.getEmail());
			tes.setProperty(Spammer.P_DESCRIPTION, e.getDescription());
			tes.setProperty(Spammer.P_SENDER, e.getSender());
			tes.setProperty(Spammer.P_STATUS, e.getStatus());
		} catch (Exception e2) {
			e2.printStackTrace() ;
			log.info("error setSpamerProp " + e2.getMessage());
			return null ;
		}
		return tes;
	}





	@Override
	public Collection<Spammer> listSpammerByStatus(String status) throws Exception {
		Collection<Spammer> list = new ArrayList<Spammer>();
		try {
			String nt = Spammer.NT_NAME, proName =  Spammer.P_STATUS;
			QueryManager qm = getStorageHome().getSession().getWorkspace().getQueryManager();
			Query q = qm.createQuery("SELECT "+proName+" FROM " + nt + " WHERE " + proName + " = '" + status +"'", Query.SQL);
			NodeIterator it = q.execute().getNodes() ;
			while (it.hasNext()) {
				list.add(getSpamerProp(it.nextNode()));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" error listSpamerByStatus " +e.getMessage());
		}
		return list;
	}





	@Override
	public Collection<Spammer> searchSpammerByEmail(String email)
			throws Exception {
		Collection<Spammer> list = new ArrayList<Spammer>();
		try {
			String domain = email;
			if(email.contains("@") && email.split("@").length > 0) domain = email.split("@")[1];
			if(domain != null && !domain.isEmpty()) {
				String nt = Spammer.NT_NAME, proName =  Spammer.P_SENDER;
				QueryManager qm = getStorageHome().getSession().getWorkspace().getQueryManager();
				Query q = qm.createQuery("SELECT "+proName+" FROM " + nt + " WHERE " + proName + " LIKE '%" + domain +"%'", Query.SQL);
				NodeIterator it = q.execute().getNodes() ;
				while (it.hasNext()) {
					list.add(getSpamerProp(it.nextNode()));
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" error listSpamerByStatus " +e.getMessage());
		}
		return list;
	}
}
