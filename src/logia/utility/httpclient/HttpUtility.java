package logia.utility.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * The Class HttpUtility.
 * 
 * @author Paul Mai
 */
public abstract class HttpUtility {

	/** The set cookie key. */
	private final String          SET_COOKIE_KEY = "Set-Cookie";

	/** The session cookie. */
	protected String              SESSION_COOKIE = "JSESSIONID";

	/** The cookie key. */
	public static final String    COOKIE_KEY     = "Cookie";

	/** The content type. */
	public static final String    CONTENT_TYPE   = "Content-Type";

	/** The http conn. */
	protected HttpURLConnection   httpConn;

	/** The session. */
	protected String              session;

	/** The request url. */
	protected String              requestURL;

	/** The is use caches. */
	protected boolean             isUseCaches;

	/** The headers. */
	protected Map<String, String> headers;

	/** The params. */
	protected Map<String, String> params;

	/** The request params. */
	protected StringBuffer        requestParams  = new StringBuffer();

	/** The timeout. */
	protected int                 timeout;

	/**
	 * Instantiates a new http utility.
	 *
	 * @param requestURL the request url
	 * @param isUseCaches the is use caches
	 * @param headers the headers
	 * @param params the params
	 * @param timeout the timeout
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpUtility(String requestURL, boolean isUseCaches, Map<String, String> headers, Map<String, String> params, int timeout)
			throws IOException {
		this.requestURL = requestURL;
		this.isUseCaches = isUseCaches;
		this.headers = headers;
		this.params = params;
		this.timeout = timeout;
		this.setParameters();
	}

	/**
	 * Disconnect.
	 */
	public void disconnect() {
		if (this.httpConn != null) {
			this.httpConn.disconnect();
		}
	}

	/**
	 * Execute the http request.
	 *
	 * @return the response code
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TimeoutException the timeout exception
	 */
	public abstract int execute() throws IOException, TimeoutException;

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public List<String> getCookies() {
		Map<String, List<String>> headerMaps = this.httpConn.getHeaderFields();
		List<String> cookies = headerMaps.get(this.SET_COOKIE_KEY);
		return cookies;
	}

	/**
	 * Gets the response content.
	 *
	 * @return the response content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getResponseContent() throws IOException {
		InputStream inputStream = null;
		StringBuffer jb = new StringBuffer();
		if (this.httpConn != null) {
			try {
				inputStream = this.httpConn.getInputStream();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			throw new IOException("Connection is not established.");
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			ArrayList<String> response = new ArrayList<String>();
			String line = "";
			while ((line = reader.readLine()) != null) {
				jb.append(line);
				response.add(line);
			}
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return jb.toString();
	}

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public String getSessionId() {
		String sessionId = null;
		for (String cookie : this.getCookies()) {
			if (cookie.toLowerCase().contains("sessionid")) {
				String[] arr = cookie.split(";");
				for (String element : arr) {
					if (element.toLowerCase().contains("sessionid")) {
						sessionId = element;
					}
				}
			}
		}
		return sessionId;
	}

	/**
	 * Sets the headers.
	 */
	protected void setHeaders() {
		for (String key : this.headers.keySet()) {
			this.httpConn.setRequestProperty(key, this.headers.get(key));
		}
	}

	/**
	 * Sets the parameters, encode them using URLEncoder
	 */
	protected void setParameters() {
		Iterator<String> paramIterator = this.params.keySet().iterator();
		while (paramIterator.hasNext()) {
			String key = paramIterator.next();
			String value = this.params.get(key);
			try {
				this.requestParams.append(URLEncoder.encode(key, "UTF-8"));
				this.requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
			}
			catch (UnsupportedEncodingException e) {
				this.requestParams.append("=").append(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			this.requestParams.append("&");
		}
	}
}
