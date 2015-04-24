package logia.utility.httpclient;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * The Class HttpSendPost.
 * 
 * @author Paul Mai
 */
public class HttpSendPost extends HttpUtility {

	/**
	 * Instantiates a new http send post.
	 *
	 * @param proxy the proxy
	 * @param requestURL the request url
	 * @param isUseCaches the is use caches
	 * @param headers the headers
	 * @param params the params
	 * @param timeout the timeout
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPost(Proxy proxy, String requestURL, boolean isUseCaches, Map<String, String> headers, Map<String, String> params, int timeout)
			throws IOException {
		super(requestURL, isUseCaches, headers, params, timeout);
		URL url = new URL(requestURL);
		this.httpConn = (HttpURLConnection) url.openConnection(proxy);
		this.httpConn.setConnectTimeout(timeout);
		this.httpConn.setUseCaches(isUseCaches);
		this.httpConn.setRequestMethod("POST");
		this.httpConn.setDoInput(true); // true indicates the server returns response
		this.httpConn.setDoOutput(true); // true indicates POST request
		this.setHeaders();
	}

	/**
	 * Instantiates a new http send post.
	 *
	 * @param requestURL the request url
	 * @param isUseCaches the is use caches
	 * @param headers the headers
	 * @param params the params
	 * @param timeout the connect timeout value in milliseconds
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPost(String requestURL, boolean isUseCaches, Map<String, String> headers, Map<String, String> params, int timeout)
			throws IOException {
		super(requestURL, isUseCaches, headers, params, timeout);
		URL url = new URL(requestURL);
		this.httpConn = (HttpURLConnection) url.openConnection();
		this.httpConn.setConnectTimeout(timeout);
		this.httpConn.setUseCaches(isUseCaches);
		this.httpConn.setRequestMethod("POST");
		this.httpConn.setDoInput(true); // true indicates the server returns response
		this.httpConn.setDoOutput(true); // true indicates POST request
		this.setHeaders();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logia.utility.httpclient.HttpUtility#doSend()
	 */
	@Override
	public int execute() throws IOException, TimeoutException {
		if (this.params != null && this.params.size() > 0) {
			OutputStreamWriter writer = new OutputStreamWriter(this.httpConn.getOutputStream());
			writer.write(this.requestParams.toString());
			writer.flush();
		}
		return this.httpConn.getResponseCode();
	}

}
