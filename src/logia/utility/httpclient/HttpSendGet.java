package logia.utility.httpclient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * The Class HttpSendGet.
 * 
 * @author Paul Mai
 */
public class HttpSendGet extends HttpUtility {

	/**
	 * Instantiates a new http send get.
	 *
	 * @param proxy the proxy
	 * @param requestURL the request url
	 * @param isUseCaches the is use caches
	 * @param headers the headers
	 * @param params the params
	 * @param timeout the timeout
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendGet(Proxy proxy, String requestURL, boolean isUseCaches, Map<String, String> headers, Map<String, String> params, int timeout)
			throws IOException {
		super(requestURL, isUseCaches, headers, params, timeout);
		URL url;
		if (this.requestParams.length() > 0) {
			url = new URL(requestURL + "?" + this.requestParams.toString());
		}
		else {
			url = new URL(requestURL);
		}
		this.httpConn = (HttpURLConnection) url.openConnection(proxy);
		this.httpConn.setConnectTimeout(timeout);
		this.httpConn.setUseCaches(isUseCaches);
		this.httpConn.setRequestMethod("GET");
		this.httpConn.setDoInput(true); // true indicates the server returns response
		this.httpConn.setDoOutput(false);// false indicates GET request
		this.setHeaders();
	}

	/**
	 * Instantiates a new http send get.
	 *
	 * @param requestURL the request url
	 * @param isUseCaches the is use caches
	 * @param headers the headers
	 * @param params the params
	 * @param timeout the connect timeout value in milliseconds
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendGet(String requestURL, boolean isUseCaches, Map<String, String> headers, Map<String, String> params, int timeout)
			throws IOException {
		super(requestURL, isUseCaches, headers, params, timeout);
		URL url;
		if (this.requestParams.length() > 0) {
			url = new URL(requestURL + "?" + this.requestParams.toString());
		}
		else {
			url = new URL(requestURL);
		}
		this.httpConn = (HttpURLConnection) url.openConnection();
		this.httpConn.setConnectTimeout(timeout);
		this.httpConn.setUseCaches(isUseCaches);
		this.httpConn.setRequestMethod("GET");
		this.httpConn.setDoInput(true); // true indicates the server returns response
		this.httpConn.setDoOutput(false);// false indicates GET request
		this.setHeaders();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logia.utility.httpclient.HttpUtility#doSend()
	 */
	@Override
	public int execute() throws IOException, TimeoutException {
		return this.httpConn.getResponseCode();
	}
}
