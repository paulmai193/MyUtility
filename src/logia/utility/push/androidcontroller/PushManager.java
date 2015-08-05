package logia.utility.push.androidcontroller;

import java.util.Map;

/**
 * this interface define the template for the push managers .
 *
 */
public interface PushManager {

	/**
	 * for configure the push notification accounts instance or to register the services over the push notification services like APNS OR GCM
	 *
	 * @param confString
	 * @param password
	 * @return String
	 */
	public String configureAccount(String confString, String password);

	/**
	 * This method actually sent the message to the device and return the status of the notification to the caller resources.
	 * 
	 * @param pushString
	 * @param message
	 * @param deviceName
	 * @return String
	 */
	public boolean sendMessage(String pushString, String message, String deviceName, String screen, Map<String, String> param, boolean show);

}