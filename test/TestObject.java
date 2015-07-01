import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logia.utility.json.JsonTool;
import logia.utility.json.annotaion.JsonKey;

import com.google.gson.JsonObject;

/**
 * The Class TestObject.
 */
@logia.utility.json.annotaion.JsonObject
public class TestObject {

	/** The m integer. */
	@JsonKey(key = "MyInt")
	int          integer;

	/** The m string. */
	@JsonKey(key = "MyString")
	String       string;

	/** The m boolean. */
	@JsonKey(key = "MyBoolean")
	Boolean      bool;

	/** The m float. */
	@JsonKey(key = "MyFloat")
	float        flot;

	/** The list. */
	@JsonKey(key = "list")
	List<String> list;

	/** The time. */
	Date         time;

	/**
	 * Instantiates a new test object.
	 */
	public TestObject() {

	}

	/**
	 * Instantiates a new test object.
	 *
	 * @param i the i
	 * @param s the s
	 * @param b the b
	 * @param f the f
	 */
	public TestObject(int i, String s, boolean b, float f, List<String> l, Date t) {
		this.integer = i;
		this.string = s;
		this.bool = b;
		this.flot = f;
		this.list = l;
		this.time = t;
	}

	/**
	 * @return the flot
	 */
	public float getFlot() {
		return this.flot;
	}

	/**
	 * @return the integer
	 */
	public int getInteger() {
		return this.integer;
	}

	/**
	 * @return the list
	 */
	public List<String> getList() {
		return this.list;
	}

	/**
	 * @return the string
	 */
	public String getString() {
		return this.string;
	}

	/**
	 * @return the time
	 */
	@JsonKey(key = "mytime")
	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.time);
	}

	/**
	 * @return the bool
	 */
	public boolean isBool() {
		return this.bool;
	}

	/**
	 * @param bool the bool to set
	 */
	public void setBool(boolean bool) {
		this.bool = bool;
	}

	/**
	 * @param flot the flot to set
	 */
	public void setFlot(float flot) {
		this.flot = flot;
	}

	/**
	 * @param integer the integer to set
	 */
	public void setInteger(int integer) {
		this.integer = integer;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}

	/**
	 * @param time the time to set
	 * @throws ParseException
	 */
	@JsonKey(key = "mytime")
	public void setTime(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.time = sdf.parse(time);
	}

	public static void main(String[] args) {
		// tojson
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		TestObject testObject = new TestObject(12, "Muoi hai", true, (float) 0.683, list, new Date());
		JsonObject json = JsonTool.toJsonObject(testObject);
		System.out.println(json);

		// from json
		testObject = JsonTool.fromJsonObject(json, TestObject.class);
		System.out.println(JsonTool.toJsonObject(testObject));
	}
}
