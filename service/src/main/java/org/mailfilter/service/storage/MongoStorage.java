package org.mailfilter.service.storage;

import java.lang.reflect.UndeclaredThrowableException;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ValueParam;
import org.picocontainer.Startable;

public class MongoStorage implements Startable {

  /** . */
  private DB db;
  
  /** . */
  private final String host;

  /** . */
  private final int port;
  
  private final String db_name;
  
  /**
   * Create a mongo store with the specified init params.
   *
   * @param params the init params
   */
  public MongoStorage(InitParams params) {

      //
      ValueParam hostParam = params.getValueParam("host");
      ValueParam portParam = params.getValueParam("port");
      ValueParam dbParam = params.getValueParam("data");

      //
      String host = hostParam != null ? hostParam.getValue().trim() : "localhost";
      String dbName = dbParam != null ? dbParam.getValue().trim() : "mailfilter";
      int port = portParam != null ? Integer.parseInt(portParam.getValue().trim()) : 27017;

      this.host = host;
      this.port = port;
      this.db_name = dbName;
  }

  /**
   * Create a mongo store with <code>localhost</code> host and <code>27017</code> port.
   */
  public MongoStorage() {
      this("localhost", 27017, "mailfilter");
  }
  
  /**
   * Create a mongo store with the specified connection parameters.
   *
   * @param host the host
   * @param port the port
   * @param dbName the data base name
   */
  public MongoStorage(String host, int port, String dbName) {
      this.host = host;
      this.port = port;
      this.db_name = dbName;
  }
  
  public DB getDB() {
    return db;
  }

  
  @Override
  public void start() {
    try {
      MongoClient mongo = new MongoClient(host, port);
      this.db = mongo.getDB(db_name);
      System.err.println("============>MongoStorage===================== ");;
      //DB admin = mongo.getDB("admin");
      //DBObject cmd = new BasicDBObject("shardCollection",new BasicDBObject()
                                       //.append("social.comment", new BasicDBObject("_id", "hashed")));
      //System.err.println("============>MongoStorage: "+admin.command(cmd));;
    } catch (MongoException e) {
    	e.printStackTrace();
      throw new UndeclaredThrowableException(e);
    } catch (Exception e) {
    	e.printStackTrace();
      throw new UndeclaredThrowableException(e);
    }
  }

  @Override
  public void stop() {
    
  }

}
