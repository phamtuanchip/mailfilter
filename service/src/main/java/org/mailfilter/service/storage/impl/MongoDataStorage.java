
package org.mailfilter.service.storage.impl;

import java.util.Collection;
import java.util.Set;

import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.MongoStorage;

import com.mongodb.DBCollection;
 
public class MongoDataStorage implements DataStorage {
	MongoStorage storage_;
	
	
	 /**
	   * Gets the collection specified by name
	   * 
	   * @param name the name of collection.
	   * @return
	   */
	  protected DBCollection getCollection(String name) {
	    return storage_.getDB().getCollection(name);
	  }
	  
	  /**
	   * Gets the mongo storage
	   * @return
	   */
	  public MongoStorage getMongoStorage() {
	    return storage_;
	  }
	  
	  /**
	   * Gets list of collections on specified DB
	   * @return
	   */
	  protected Set<String> getCollections() {
	    return storage_.getDB().getCollectionNames();
	  }

	
	public MongoDataStorage(MongoStorage dataStore){
		storage_ = dataStore;
	}
	
	@Override
	public Spammer getSpammerById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Spammer> listSpammer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Spammer> listSpammerByStatus(String status)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSpammer(Spammer s) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spammer updateSpammer(Spammer s) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spammer addSpammer(Spammer s) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Spammer> updateSpammers(Collection<Spammer> list)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Spammer> addSpammers(Collection<Spammer> list)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Spammer> searchSpammerByEmail(String email)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	 
 
	 
}
