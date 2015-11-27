package logia.utility.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 * The Class HttpUtility.
 * 
 * @author Paul Mai
 */
public abstract class HttpUtility {

	/** The Constant HEADER_CONTENT_TYPE. */
	public static final String    HEADER_CONTENT_TYPE = "Content-Type";

	/** The Constant HEADER_COOKIE. */
	public static final String    HEADER_COOKIE       = "Cookie";

	/** The Constant HEADER_USER_AGENT. */
	public static final String    HEADER_USER_AGENT   = "User-Agent";

	/** The cookie store. */
	private CookieStore           cookieStore;

	/** The headers. */
	protected Map<String, String> headers;

	/** The http client. */
	protected HttpClient          httpClient;

	/** The http context. */
	protected HttpClientContext   httpContext;

	/** The http host. */
	protected HttpHost            httpHost;

	/** The http request. */
	protected HttpRequest         httpRequest;

	/** The http response. */
	protected HttpResponse        httpResponse;

	/** The logger. */
	protected final Logger        LOGGER              = Logger.getLogger(this.getClass());

	/** The params. */
	protected Map<String, String> params;

	/** The request url. */
	protected String              requestURL;

	/**
	 * Instantiates a new http utility.
	 *
	 * @param httpHost the host
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpUtility(HttpHost httpHost, String requestURL, Map<String, String> headers, Map<String, String> params) throws IOException {
		this.httpHost = httpHost;
		this.requestURL = requestURL;
		this.headers = headers;
		this.params = params;

		this.httpContext = HttpClientContext.create();
		this.httpClient = HttpClientBuilder.create().build();
	}

	/**
	 * Instantiates a new http utility.
	 *
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpUtility(String requestURL, Map<String, String> headers, Map<String, String> params) throws IOException {
		this.requestURL = requestURL;
		this.headers = headers;
		this.params = params;

		this.httpContext = HttpClientContext.create();
		this.httpClient = HttpClientBuilder.create().build();
	}

	/**
	 * Execute the http request.
	 *
	 * @return the response code
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TimeoutException the timeout exception
	 */
	public int execute() throws IOException, TimeoutException {
		if (this.httpHost != null) {
			this.httpResponse = this.httpClient.execute(this.httpHost, this.httpRequest, this.httpContext);
		}
		else {
			this.httpResponse = this.httpClient.execute((HttpUriRequest) this.httpRequest, this.httpContext);
		}
		this.cookieStore = this.httpContext.getCookieStore();
		return this.httpResponse.getStatusLine().getStatusCode();
	}

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public List<Cookie> getListCookies() {
		return this.cookieStore.getCookies();
	}

	/**
	 * Gets the response content.
	 *
	 * @return the response content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getResponseContent() throws IOException {
		InputStream inputStream = null;
		// StringBuffer buffer = new StringBuffer();
		if (this.httpClient != null) {
			inputStream = this.httpResponse.getEntity().getContent();
		}
		else {
			throw new IOException("Connection is not established.");
		}
		// try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
		// ArrayList<String> response = new ArrayList<String>();
		// String line = "";
		// while ((line = reader.readLine()) != null) {
		// buffer.append(line);
		// response.add(line);
		// }
		// }
		String responseContent = IOUtils.toString(inputStream, Charsets.UTF_8);
		return responseContent;
	}

	/**
	 * Sets the cookie.
	 *
	 * @param cookie the new cookie
	 */
	public void setCookie(Cookie cookie) {
		this.cookieStore.addCookie(cookie);
		this.httpContext.setCookieStore(this.cookieStore);
	}

	/**
	 * Sets the cookies.
	 *
	 * @param cookies the new cookies
	 */
	public void setCookies(List<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			this.setCookie(cookie);
		}
	}

	/**
	 * Sets the http context.
	 *
	 * @param httpContext the httpContext to set
	 */
	public void setHttpContext(HttpClientContext httpContext) {
		this.httpContext = httpContext;
	}

	/**
	 * Sets the headers.
	 */
	protected void setHeaders() {
		if (this.headers == null) {
			return;
		}
		for (Entry<String, String> header : this.headers.entrySet()) {
			this.httpRequest.setHeader(header.getKey(), header.getValue());
		}
	}

	/**
	 * Sets the parameters, encode them using URLEncoder.
	 */
	protected abstract void setParameters();
}
