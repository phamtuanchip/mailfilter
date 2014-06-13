 
package org.estudy.webservice;

import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.rest.ContainerResponseWriter;
import org.exoplatform.services.rest.impl.*;
import org.exoplatform.services.rest.tools.DummyContainerResponseWriter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayInputStream;
import java.net.URI;
 
public abstract class AbstractResourceTest extends BaseTest {


  public ContainerResponse service(String method,
                                   String requestURI,
                                   String baseURI,
                                   MultivaluedMap<String, String> headers,
                                   byte[] data,
                                   ContainerResponseWriter writer) throws Exception {
	  try {
		
	
  RequestLifeCycle.begin(container);
    if (headers == null)
      headers = new MultivaluedMapImpl();

    ByteArrayInputStream in = null;
    if (data != null)
      in = new ByteArrayInputStream(data);

    EnvironmentContext envctx = new EnvironmentContext();
    HttpServletRequest httpRequest = new MockHttpServletRequest(in,
                                                                in != null ? in.available() : 0,
                                                                method,
                                                                new InputHeadersMap(headers));
    envctx.put(HttpServletRequest.class, httpRequest);
    EnvironmentContext.setCurrent(envctx);
    ContainerRequest request = new ContainerRequest(method,
                                                    new URI(requestURI),
                                                    new URI(baseURI),
                                                    in,
                                                    new InputHeadersMap(headers));
    ContainerResponse response = new ContainerResponse(writer);
    requestHandler.handleRequest(request, response);
    RequestLifeCycle.end();
    return response;
	  } catch (Exception e) {
		  e.printStackTrace();
		return null;
	}
  }

  public ContainerResponse service(String method,
                                   String requestURI,
                                   String baseURI,
                                   MultivaluedMap<String, String> headers,
                                   byte[] data) throws Exception {
    return service(method, requestURI, baseURI, headers, data, new DummyContainerResponseWriter());

  }

}
