package logia.utility.push.androidcontroller;

import org.apache.log4j.Logger;

public final class PushManagerFactory {

	private final Logger             LOGGER      = Logger.getLogger(PushManagerFactory.class);

	private static AndroidGcmManager andInstance = null;

	public static PushManager getInstance() throws Exception {
		PushManagerFactory.andInstance = new AndroidGcmManager();
		return PushManagerFactory.andInstance;
	}
}