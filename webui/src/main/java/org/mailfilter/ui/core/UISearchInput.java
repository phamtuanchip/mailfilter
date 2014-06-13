 
 
package org.mailfilter.ui.core;

import org.exoplatform.webui.form.UIFormStringInput;
 
public class UISearchInput extends UIFormStringInput {
  public UISearchInput(String id, String bean, String value){
    super(id, bean);
    value_ = value ;
  }
}
