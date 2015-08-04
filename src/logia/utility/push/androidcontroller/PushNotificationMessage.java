package logia.utility.push.androidcontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

public class PushNotificationMessage {

	private Logger _logger = Logger.getLogger(PushNotificationMessage.class.getName());

	public boolean sendPushNotificationAndroid(String IdPush, String pushMsg) {
		boolean gcmsend = true;
		try {

			PushManager gcm = PushManagerFactory.getInstance();
			Map<String, String> pushParam = new HashMap<String, String>();
			pushParam.put("Message", "This");
			gcmsend = gcm.sentMessage(IdPush, pushMsg, "Android", "View", pushParam, true);

		}
		catch (Exception ex) {
			this._logger.error(ex.getMessage(), ex);
			gcmsend = false;
		}
		return gcmsend;
	}

	public static String RandomStringGenerator(int totchar) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(totchar);
		for (int i = 0; i < totchar; i++) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}

}