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
		if (requestParams.length() > 0) {
			url = new URL(requestURL + "?" + requestParams.toString());
		}
		else {
			url = new URL(requestURL);
		}
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setConnectTimeout(timeout);
		httpConn.setUseCaches(isUseCaches);
		httpConn.setRequestMethod("GET");
		httpConn.setDoInput(true); // true indicates the server returns response
		httpConn.setDoOutput(false);// false indicates GET request
		setHeaders();
	}
	
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
		if (requestParams.length() > 0) {
			url = new URL(requestURL + "?" + requestParams.toString());
		}
		else {
			url = new URL(requestURL);
		}
		httpConn = (HttpURLConnection) url.openConnection(proxy);
		httpConn.setConnectTimeout(timeout);
		httpConn.setUseCaches(isUseCaches);
		httpConn.setRequestMethod("GET");
		httpConn.setDoInput(true); // true indicates the server returns response
		httpConn.setDoOutput(false);// false indicates GET request
		setHeaders();
	}

	/* (non-Javadoc)
	 * @see logia.utility.httpclient.HttpUtility#doSend()
	 */
	@Override
	public int execute() throws IOException, TimeoutException {
		return httpConn.getResponseCode();
	}
}
