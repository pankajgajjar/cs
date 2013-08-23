package app.cs.pim;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.data.webservices.rest.RestClient;

@Component
public class PIMRepository {

	private static final String CHARSET = "ISO-8859-1,utf-8;q=0.7,*;q=0.3";
	private static final String ACCEPT_CHARSET = "Accept-Charset";
	private static final String ACCEPTEDTYPES = "*/*";
	private static final String ACCEPT = "Accept";
	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	private static final String X_REQUESTED_WITH = "X-Requested-With";
	private static final String USER_AGENT_INFO = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.63 Safari/537.31";
	private static final String USER_AGENT = "User-Agent";
	private static final String HOSTIP = "192.168.135.108";
	private static final String HOST = "Host";
	private static final String LANGUAGE = "en-US,en;q=0.8";
	private static final String ACCEPT_LANGUAGE = "Accept-Language";
	private final String BASE_URL = "http://192.168.135.108/CS13.0/admin/rest/pim/list/";
	private RestClient client;

	@Autowired
	public PIMRepository(RestClient client) {
		this.client = client;

	}

	public String getProductsFor(String id) {

		String url = formUrl(id);
		Map<String, String> headerParameters = new HashMap<String, String>();
		headerParameters.put(ACCEPT_LANGUAGE, LANGUAGE);
		headerParameters.put(HOST, HOSTIP);
		headerParameters.put(USER_AGENT, USER_AGENT_INFO);
		headerParameters.put(X_REQUESTED_WITH, XML_HTTP_REQUEST);
		headerParameters.put(ACCEPT, ACCEPTEDTYPES);
		headerParameters.put(ACCEPT_CHARSET, CHARSET);
		return client.get(url, headerParameters);

	}

	private String formUrl(String id) {
		return id == null || id == "" ? BASE_URL : BASE_URL + id;
	}

}