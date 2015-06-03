package logia.utility.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import logia.utility.json.annotaion.JsonKey;
import logia.utility.reflection.ClassUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * The Class JsonTool.
 * 
 * @author Paul Mai
 */
public class JsonTool {

	/**
	 * Merge json.
	 *
	 * @param json the json
	 * @return the json object
	 */
	public static JsonObject mergeJson(JsonObject... json) {
		JsonObject mergedJson = new JsonObject();
		for (JsonObject element : json) {
			for (Map.Entry<String, JsonElement> entry : element.entrySet()) {
				mergedJson.add(entry.getKey(), entry.getValue());
			}
		}
		return mergedJson;
	}

	/**
	 * Parse object to json object. This object require implement @JsonObject annotation
	 *
	 * @param object the object
	 * @return the json object
	 */
	public static JsonObject toJsonObject(Object object) {
		if (isJsonObject(object)) {
			Class<?> clazz = object.getClass();
			JsonObject jsonObject = new JsonObject();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation fieldAnnotation = field.getAnnotation(JsonKey.class);
				if (fieldAnnotation != null) {
					JsonKey key = (JsonKey) fieldAnnotation;
					try {
						Method getter = ClassUtil.findGetter(clazz, field);
						if (getter != null) {
							Object value = getter.invoke(object);

							if (value == null) {
								jsonObject.addProperty(key.key(), "");
							}
							else if (value instanceof Boolean) {
								jsonObject.addProperty(key.key(), (Boolean) value);
							}
							else if (value instanceof Character) {
								jsonObject.addProperty(key.key(), (Character) value);
							}
							else if (value instanceof Number) {
								jsonObject.addProperty(key.key(), (Number) value);
							}
							else if (value instanceof String) {
								jsonObject.addProperty(key.key(), (String) value);
							}
							else if (value instanceof JsonElement) {
								jsonObject.add(key.key(), (JsonElement) value);
							}
							else if (isJsonObject(value)) {
								jsonObject.add(key.key(), toJsonObject(value));
							}
							else {
								jsonObject.addProperty(key.key(), value.toString());
							}
						}
					}
					catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			return jsonObject;
		}
		else {
			return null;
		}
	}

	/**
	 * Parse array of object to json array.
	 *
	 * @param arrayObject the array object
	 * @return the json array
	 */
	public static JsonArray toJsonArray(Object[] arrayObject) {
		JsonArray jsonArray = new JsonArray();
		for (Object object : arrayObject) {
			if (object instanceof Boolean) {
				JsonPrimitive element = new JsonPrimitive((Boolean) object);
				jsonArray.add(element);
			}
			else if (object instanceof Character) {
				JsonPrimitive element = new JsonPrimitive((Character) object);
				jsonArray.add(element);
			}
			else if (object instanceof Number) {
				JsonPrimitive element = new JsonPrimitive((Number) object);
				jsonArray.add(element);
			}
			else if (object instanceof String) {
				JsonPrimitive element = new JsonPrimitive((String) object);
				jsonArray.add(element);
			}
			else if (object instanceof JsonElement) {
				jsonArray.add((JsonElement) object);
			}
			else if (isJsonObject(object)) {
				jsonArray.add(toJsonObject(object));
			}
		}
		return jsonArray;
	}

	/**
	 * Checks if is json object.
	 *
	 * @param object the object
	 * @return true, if is json object
	 */
	private static boolean isJsonObject(Object object) {
		Class<?> clazz = object.getClass();
		Annotation clazzAnnotation = clazz.getAnnotation(logia.utility.json.annotaion.JsonObject.class);
		if (clazzAnnotation != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
