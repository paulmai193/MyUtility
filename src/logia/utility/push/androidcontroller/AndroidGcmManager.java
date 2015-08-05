package logia.utility.push.androidcontroller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * The Class AndroidGcmManager.
 * 
 * @author Paul Mai
 */
public class AndroidGcmManager implements PushManager {

	/** The logger. */
	private final Logger LOGGER     = Logger.getLogger(this.getClass());

	/** The sender. */
	private Sender       sender;

	/** The alert props. */
	private Properties   alertProps = new Properties();

	/**
	 * Instantiates a new android gcm manager.
	 *
	 * @throws Exception the exception
	 */
	public AndroidGcmManager() throws Exception {
		URL url = this.getClass().getClassLoader().getResource("notification.properties");
		this.alertProps.load(new InputStreamReader(url.openStream(), "UTF-8"));

		this.sender = new Sender(this.alertProps.getProperty("android.apikey"));
	}

	/**
	 * Configure android GSM account for the sending push notification to the android device.
	 *
	 * @param apiKey the api key
	 * @param password the password
	 * @return the string
	 */
	@Override
	public String configureAccount(String apiKey, String password) {
		String responseString = "true";
		this.sender = new Sender(apiKey);
		return responseString;
	}

	/**
	 * This method send the notification to the device and return the status of the notification.
	 *
	 * @param pushString the push string
	 * @param message the message
	 * @param deviceName the device name
	 * @param screen the screen
	 * @param param the param
	 * @param show the show
	 * @return true, if successful
	 */
	@Override
	public boolean sendMessage(String pushString, String message, String deviceName, String screen, Map<String, String> param, boolean show) {
		boolean responseString = true;
		if (this.sender != null) {
			try {
				Builder builder = new Message.Builder();
				for (String key : param.keySet()) {
					builder.addData(key, param.get(key));
				}
				builder.addData(this.alertProps.getProperty("android.messagekey"), message);
				builder.collapseKey(PushNotificationMessage.RandomStringGenerator(9));
				Message gcmMessage = builder.build();
				Result result = this.sender.send(gcmMessage, pushString, 5);
				if (result != null) {
					if (result.getMessageId() != null) {
						String canonicalRegId = result.getCanonicalRegistrationId();
						if (canonicalRegId != null) {
							this.LOGGER.info("Canonical Registration: " + canonicalRegId);
						}
					}
					else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							this.LOGGER.info("Error Code Name: " + result.getErrorCodeName());
						}
					}
				}
			}
			catch (IOException e) {
				this.LOGGER.error(e.getMessage(), e);
				responseString = false;
			}
		}
		else {
			responseString = false;
		}
		return responseString;
	}
}