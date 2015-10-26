package logia.utility.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;

/**
 * The Class HttpSendPut.
 * 
 * @author Paul Mai
 */
public class HttpSendPut extends HttpUtility {

	/**
	 * Instantiates a new http send put.
	 *
	 * @param host the host
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPut(HttpHost host, String requestURL, Map<String, String> headers, Map<String, String> params) throws IOException {
		super(host, requestURL, headers, params);
		this.httpRequest = new HttpPut(this.requestURL);
		this.setHeaders();
		this.setParameters();
	}

	/**
	 * Instantiates a new http send put.
	 *
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPut(String requestURL, Map<String, String> headers, Map<String, String> params) throws IOException {
		super(requestURL, headers, params);
		this.httpRequest = new HttpPut(this.requestURL);
		this.setHeaders();
		this.setParameters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logia.utility.httpclient.HttpUtility#setParameters()
	 */
	@Override
	protected void setParameters() {
		if (this.params == null) {
			return;
		}
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for (Entry<String, String> param : this.params.entrySet()) {
			urlParameters.add(new BasicNameValuePair(param.getKey(), param.getValue()));
		}
		try {
			((HttpEntityEnclosingRequest) this.httpRequest).setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		catch (UnsupportedEncodingException e) {
			this.LOGGER.error(e);
		}
	}

}
