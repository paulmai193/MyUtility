package logia.utility.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;

/**
 * The Class HttpSendGet.
 * 
 * @author Paul Mai
 */
public class HttpSendGet extends HttpUtility {

	/** The request params. */
	private StringBuffer requestParams = new StringBuffer();

	/**
	 * Instantiates a new http send GET method.
	 *
	 * @param host the host
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendGet(HttpHost host, String requestURL, Map<String, String> headers, Map<String, String> params) throws IOException {
		super(host, requestURL, headers, params);
		this.setParameters();
		this.httpRequest = new HttpGet(this.requestURL);
		this.setHeaders();
		if (this.requestParams.length() > 0) {
			this.requestURL = this.requestURL + "?" + this.requestParams.toString();
		}

	}

	/**
	 * Instantiates a new http send GET method.
	 *
	 * @param url the request url
	 * @param headers the headers
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
		super(url, headers, params);
		this.setParameters();
		if (this.requestParams.length() > 0) {
			this.requestURL = this.requestURL + "?" + this.requestParams.toString();
		}
		this.httpRequest = new HttpGet(this.requestURL);
		this.setHeaders();
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
		for (Entry<String, String> param : this.params.entrySet()) {
			String key = param.getKey();
			String value = param.getValue();
			try {
				this.requestParams.append(URLEncoder.encode(key, "UTF-8"));
				this.requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
			}
			catch (UnsupportedEncodingException e) {
				this.requestParams.append("=").append(value);
			}
			this.requestParams.append("&");
		}
	}
}
