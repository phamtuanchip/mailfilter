
package org.mailfilter.webservice;

import java.util.Collection;

import org.mailfilter.service.model.Spammer;

public class JSONData {

  private Collection<Spammer> list;
  private Collection<String> info ;

  public Collection<String> getInfo() {
    return info;
  }
  
  public void setInfo(Collection<String> info) {
    this.info = info;
  }


  public Collection<Spammer> getList() {
    return list;
  }

  public void setList(Collection<Spammer> list) {
    this.list = list;
  }
}