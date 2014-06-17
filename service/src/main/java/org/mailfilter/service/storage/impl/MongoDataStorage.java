
package org.mailfilter.service.storage.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jcr.ItemExistsException;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.mailfilter.service.model.Spammer;
import org.mailfilter.service.model.SpammerMongoEntity;
import org.mailfilter.service.storage.DataStorage;
import org.mailfilter.service.storage.MongoStorage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoDataStorage implements DataStorage {
	MongoStorage storage_;


	enum CollectionName {

		FILTER_COLLECTION("spammer") {
			@Override
			protected void ensureIndex(DBCollection got) {
				got.ensureIndex(new BasicDBObject(SpammerMongoEntity.sender.getName(), -1).append(SpammerMongoEntity.email.getName(), 1)
						.append(SpammerMongoEntity.description.getName(), 1));
			}
		} ;

		private final String collectionName;

		private CollectionName(String name) {
			this.collectionName = name;
		}


		public DBCollection getCollection(MongoDataStorage mongoStorage) {
			String name = collectionName();
			Set<String> names = mongoStorage.getCollections();
			boolean isExistingCollection = names.contains(name);
			DBCollection got = mongoStorage.getCollection(name);
			//
			if (isExistingCollection == false) {
				ensureIndex(got);
			}
			return got;
		}

		protected abstract void ensureIndex(DBCollection got);

		/**
		 * Gets the collection name with tenant name
		 * @return
		 */
		private String collectionName() {
			return this.collectionName;
		}
	}




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

	private boolean isExist(Spammer s) {
		DBCollection collection = CollectionName.FILTER_COLLECTION.getCollection(this);
		BasicDBObject query = new BasicDBObject();
		query.append(SpammerMongoEntity.sender.getName(), s.getSender());
		BasicDBObject entity = (BasicDBObject) collection.findOne(query);
		return entity != null;
	}
	@Override
	public Spammer getSpammerById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Spammer> listSpammer() throws Exception {
		DBCollection connectionColl = CollectionName.FILTER_COLLECTION.getCollection(this);

		BasicDBObject query = new BasicDBObject();
		//sort by time DESC
		BasicDBObject sortObj = new BasicDBObject(SpammerMongoEntity.sender.getName(), -1);
		BasicDBObject fields = new BasicDBObject(SpammerMongoEntity.email.getName(), 1)
		.append(SpammerMongoEntity.status.getName(), 1);

		//int limit = 1;
		//DBCursor cur = connectionColl.find(query, fields).sort(sortObj).limit(limit);
		DBCursor cur = connectionColl.find(query, fields).sort(sortObj);
		List<Spammer> result = new LinkedList<Spammer>();
		while (cur.hasNext()) {
			BasicDBObject row = (BasicDBObject) cur.next();
			Spammer s = new Spammer();
			s.setId(row.getString(SpammerMongoEntity.id.getName()));
			s.setSender(row.getString(SpammerMongoEntity.sender.getName()));
			s.setEmail(row.getString(SpammerMongoEntity.email.getName()));
			s.setStatus(row.getString(SpammerMongoEntity.status.getName()));
			s.setDescription(row.getString(SpammerMongoEntity.description.getName()));
			result.add(s);
		}
		return result;
	}

	@Override
	public Collection<Spammer> listSpammerByStatus(String status)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSpammer(Spammer s) throws Exception {
		DBCollection connectionColl = CollectionName.FILTER_COLLECTION.getCollection(this);
		BasicDBObject query = new BasicDBObject();
		query.append(SpammerMongoEntity.id.getName(), s.getId());
		connectionColl.remove(query);
	}

	@Override
	public Spammer updateSpammer(Spammer s) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spammer addSpammer(Spammer s) throws Exception {
		try {
			Validate.notNull(s, "Spammer must not be null.");
			Validate.notNull(s.getSender(), "Domain must not be null.");
			if(isExist(s)) throw new ItemExistsException();
			DBCollection collection = CollectionName.FILTER_COLLECTION.getCollection(this);
			BasicDBObject spamer = new BasicDBObject();
			spamer.append(SpammerMongoEntity.id.getName(), s.getId());
			spamer.append(SpammerMongoEntity.sender.getName(), s.getSender());
			spamer.append(SpammerMongoEntity.status.getName(), s.getStatus());
			spamer.append(SpammerMongoEntity.description.getName(), s.getDescription());
			spamer.append(SpammerMongoEntity.email.getName(), s.getEmail());
			collection.insert(spamer);
			return s;
		} catch (IllegalArgumentException e) {
			throw new Exception(e.getMessage());
		} catch (ItemExistsException e){
			throw e;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

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
