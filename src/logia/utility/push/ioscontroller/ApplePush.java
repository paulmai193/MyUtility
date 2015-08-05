package logia.utility.push.ioscontroller;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;

/**
 * The Class ApplePush.
 * 
 * @author Paul Mai
 */
public final class ApplePush {

	/** The logger. */
	private final Logger LOGGER     = Logger.getLogger(this.getClass());

	/** The service. */
	private ApnsService  service;

	/** The alert props. */
	private Properties   alertProps = new Properties();

	/**
	 * Instantiates a new apple push.
	 *
	 * @throws Exception the exception
	 */
	public ApplePush() throws Exception {
		URL url = this.getClass().getClassLoader().getResource("notification.properties");
		this.alertProps.load(new InputStreamReader(url.openStream(), "UTF-8"));

		this.setConfig(this.alertProps.getProperty("apple.host"), this.alertProps.getProperty("apple.keypath"),
		        this.alertProps.getProperty("apple.password"), Integer.parseInt(this.alertProps.getProperty("apple.port")));
	}

	/**
	 * Send push notification to ios.
	 *
	 * @param IdPush the id push
	 * @param pushMsgJson the Json content for process
	 * @param messageDisplay the system display message
	 * @param isShow the option to allow show alert or not
	 * @return true, if successful
	 */
	public boolean sendPushNotificationIos(String IdPush, JsonObject pushMsgJson, String messageDisplay, boolean isShow) {
		boolean result = true;
		try {
			PayloadBuilder builder = APNS.newPayload();
			String payload = "";
			Set<Entry<String, JsonElement>> a = pushMsgJson.entrySet();
			for (Entry<String, JsonElement> entry : a) {
				String key = entry.getKey();
				builder.customField(key, pushMsgJson.get(key));
			}
			if (isShow) {// show push
				builder.alertBody(messageDisplay).sound("default");
			}
			payload = builder.build();
			int length = payload.getBytes("UTF-8").length;
			int cutChar = 0;
			while (length > 256) {
				cutChar = cutChar + 3;
				int cut = length - (256 - cutChar);
				messageDisplay = messageDisplay.substring(0, messageDisplay.length() - cut).concat("...");
				builder.alertBody(messageDisplay).sound("default");
				payload = builder.build();
				length = payload.getBytes("UTF-8").length;
			}
			this.LOGGER.debug("payload's value: " + payload);
			this.service.push(IdPush, payload);
		}
		catch (Exception e) {
			this.LOGGER.error(e.getMessage(), e);
			result = false;
		}
		return result;
	}

	/**
	 * Sets the config.
	 *
	 * @param host the host
	 * @param certKeyPath the cert key path
	 * @param password the password
	 * @param port the port
	 * @throws Exception the exception
	 */
	public void setConfig(String host, String certKeyPath, String password, int port) throws Exception {
		this.service = APNS.newService().withCert(certKeyPath, password).withGatewayDestination(host, port).build();
	}
}
