 
package org.mailfilter.service.storage;

import java.util.Collection;

import org.mailfilter.service.model.Spammer;
 
public interface DataStorage {
  
  // mail filer api
  
  public Spammer getSpammerById(String id) throws Exception;
  public Collection<Spammer> listSpammer() throws Exception;
  public Collection<Spammer> listSpammerByStatus(String status) throws Exception;
  public void removeSpammer(Spammer s) throws Exception;
  public Spammer updateSpammer(Spammer s) throws Exception;
  public Spammer addSpammer(Spammer s) throws Exception;
  public Collection<Spammer> updateSpammers(Collection<Spammer> list) throws Exception;
  public Collection<Spammer> addSpammers(Collection<Spammer> list) throws Exception;
  public Collection<Spammer> searchSpammerByEmail(String email) throws Exception;
  
  
  
}
