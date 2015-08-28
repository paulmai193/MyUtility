import java.util.HashMap;
import java.util.Map;

import logia.utility.httpclient.HttpSendGet;

public class TestHttp {

	public static void main(String[] args) throws Exception {
		Map<String, String> paramsText = new HashMap<String, String>();
		paramsText.put("a", "1234");

		HttpSendGet request = new HttpSendGet("http://localhost:8080/DemoWebService/rest/get", new HashMap<String, String>(), paramsText);
		System.out.println(request.execute());
		System.out.println(request.getResponseContent());
	}
}
