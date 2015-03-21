package logia.utility.httpclient;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * The Class HttpSendPut.
 * 
 * @author Paul Mai
 */
public class HttpSendPut extends HttpUtility {

	/**
	 * Instantiates a new http send put.
	 *
	 * @param requestURL the request url
	 * @param isUseCaches the is use caches
	 * @param headers the headers
	 * @param params the params
	 * @param timeout the connect timeout value in milliseconds 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPut(String requestURL, boolean isUseCaches, Map<String, String> headers, Map<String, String> params, int timeout)
			throws IOException {
		super(requestURL, isUseCaches, headers, params, timeout);
		URL url = new URL(requestURL);
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setConnectTimeout(timeout);
		httpConn.setUseCaches(isUseCaches);
		httpConn.setRequestMethod("PUT");
		httpConn.setDoInput(true); // true indicates the server returns response
		httpConn.setDoOutput(true); // true indicates POST request
		setHeaders();
	}

	/* (non-Javadoc)
	 * @see logia.utility.httpclient.HttpUtility#doSend()
	 */
	@Override
	public int execute() throws IOException {
		if (params != null && params.size() > 0) {
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write(requestParams.toString());
			writer.flush();
		}
		return httpConn.getResponseCode();
	}

}
