import java.io.File;
import java.util.HashMap;
import java.util.Map;

import logia.utility.httpclient.HttpSendPostMultipart;

public class TestHttp {

	public static void main(String[] args) throws Exception {
		Map<String, String> paramsText = new HashMap<String, String>();
		paramsText.put("videoid", "-1000");

		Map<String, File> paramsFile = new HashMap<String, File>();
		paramsFile.put("file", new File("D:/OneDrive/Music/Vietnamese/100PhanTram-HungCuong_k5e7.mp3"));
		paramsFile.put("thumb", new File("D:/OneDrive/Music/Vietnamese/100PhanTram-HungCuong_k5e7.mp3"));

		HttpSendPostMultipart post = new HttpSendPostMultipart("http://192.168.1.4:8080/nowktv/studio/uploadrawvideo", new HashMap<String, String>(),
		        paramsText, paramsFile);
		System.out.println(post.execute());
		System.out.println(post.getResponseContent());
	}
}
