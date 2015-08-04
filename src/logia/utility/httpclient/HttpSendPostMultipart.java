package logia.utility.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * The Class HttpSendPostMultipart.
 * 
 * @author Paul Mai
 */
public class HttpSendPostMultipart extends HttpUtility {

	/** The file part. */
	private Map<String, File> filePart;

	/**
	 * Instantiates a new http send post multipart.
	 *
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param paramsText the params text
	 * @param paramsFile the params file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPostMultipart(String requestURL, Map<String, String> headers, Map<String, String> paramsText, Map<String, File> paramsFile)
	        throws IOException {
		super(requestURL, headers, paramsText);
		this.filePart = paramsFile;
		httpRequest = new HttpPost(requestURL);
		setHeaders();
		setParameters();
	}

	/**
	 * Instantiates a new http send post multipart.
	 *
	 * @param httpHost the http host
	 * @param requestURL the request url
	 * @param headers the headers
	 * @param paramsText the params text
	 * @param paramsFile the params file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HttpSendPostMultipart(HttpHost httpHost, String requestURL, Map<String, String> headers, Map<String, String> paramsText,
	        Map<String, File> paramsFile) throws IOException {
		super(httpHost, requestURL, headers, paramsText);
		this.filePart = paramsFile;
		httpRequest = new HttpPost(requestURL);
		setHeaders();
		setParameters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logia.utility.httpclient.HttpUtility#setParameters()
	 */
	@Override
	protected void setParameters() {
		MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
		for (Entry<String, String> stringParam : this.params.entrySet()) {
			multipartBuilder.addPart(stringParam.getKey(), new StringBody(stringParam.getValue(), ContentType.TEXT_PLAIN));
		}
		for (Entry<String, File> fileParam : this.filePart.entrySet()) {
			multipartBuilder.addPart(fileParam.getKey(), new FileBody(fileParam.getValue(), ContentType.DEFAULT_BINARY, fileParam.getValue()
			        .getName()));
		}
		((HttpEntityEnclosingRequest) httpRequest).setEntity(multipartBuilder.build());
	}

}
