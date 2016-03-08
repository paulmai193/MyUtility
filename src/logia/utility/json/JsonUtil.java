package logia.utility.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logia.utility.json.annotaion.JsonKey;
import logia.utility.reflection.MethodUtil;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

/**
 * The Class JsonTool.
 * 
 * @author Paul Mai
 */
public final class JsonUtil {

	private static final Logger LOGGER = Logger.getLogger(JsonUtil.class);

	/**
	 * From json array.
	 *
	 * @param <T> the generic type
	 * @param __jsonArray the json array
	 * @param __clazz the clazz
	 * @return the list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> fromJsonArray(JsonArray __jsonArray, Class<T> __clazz) {
		List _list = new ArrayList<>();
		for (JsonElement _element : __jsonArray) {
			if (_element instanceof JsonPrimitive) {
				if (__clazz.isAssignableFrom(Boolean.class) || __clazz.isAssignableFrom(boolean.class)) {
					_list.add(_element.getAsBoolean());
				}
				else if (__clazz.isAssignableFrom(Character.class) || __clazz.isAssignableFrom(char.class)) {
					_list.add(_element.getAsCharacter());
				}
				else if (__clazz.isAssignableFrom(Byte.class) || __clazz.isAssignableFrom(byte.class)) {
					_list.add(_element.getAsByte());
				}
				else if (__clazz.isAssignableFrom(Short.class) || __clazz.isAssignableFrom(short.class)) {
					_list.add(_element.getAsShort());
				}
				else if (__clazz.isAssignableFrom(Integer.class) || __clazz.isAssignableFrom(int.class)) {
					_list.add(_element.getAsInt());
				}
				else if (__clazz.isAssignableFrom(Long.class) || __clazz.isAssignableFrom(long.class)) {
					_list.add(_element.getAsLong());
				}
				else if (__clazz.isAssignableFrom(Float.class) || __clazz.isAssignableFrom(float.class)) {
					_list.add(_element.getAsFloat());
				}
				else if (__clazz.isAssignableFrom(Double.class) || __clazz.isAssignableFrom(double.class)) {
					_list.add(_element.getAsDouble());
				}
				else if (__clazz.isAssignableFrom(String.class)) {
					_list.add(_element.getAsString());
				}
			}
			else if (_element instanceof JsonObject) {
				_list.add(JsonUtil.fromJsonObject((JsonObject) _element, __clazz));
			}
		}
		return _list;
	}

	/**
	 * From json array.
	 *
	 * @param <T> the generic type
	 * @param __jsonArrayString the json array string
	 * @param __clazz the clazz
	 * @return the list
	 * @throws JsonSyntaxException the json syntax exception
	 */
	public static <T> List<T> fromJsonArray(String __jsonArrayString, Class<T> __clazz) throws JsonSyntaxException {
		JsonParser _parser = new JsonParser();
		JsonArray _jsonArray = (JsonArray) _parser.parse(__jsonArrayString);
		return JsonUtil.fromJsonArray(_jsonArray, __clazz);
	}

	/**
	 * From json object.
	 *
	 * @param <T> the generic type
	 * @param jsonObject the json object
	 * @param clazz the clazz
	 * @return the instance of T
	 */
	public static <T> T fromJsonObject(JsonObject jsonObject, Class<T> clazz) {
		if (JsonUtil.isJsonObject(clazz)) {
			try {
				T object = clazz.newInstance();

				// Iterate fields with JsonKey annotation
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation fieldAnnotation = field.getAnnotation(JsonKey.class);
					if (fieldAnnotation != null) {
						JsonKey key = (JsonKey) fieldAnnotation;
						try {
							Object value = jsonObject.get(key.key());
							Class<?> type = field.getType();
							if (value instanceof JsonPrimitive) {
								if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
									field.set(object, ((JsonPrimitive) value).getAsBoolean());
								}
								else if (type.isAssignableFrom(Character.class) || type.isAssignableFrom(char.class)) {
									field.set(object, ((JsonPrimitive) value).getAsCharacter());
								}
								else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
									field.set(object, ((JsonPrimitive) value).getAsByte());
								}
								else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
									field.set(object, ((JsonPrimitive) value).getAsShort());
								}
								else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
									field.set(object, ((JsonPrimitive) value).getAsInt());
								}
								else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
									field.set(object, ((JsonPrimitive) value).getAsLong());
								}
								else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
									field.set(object, ((JsonPrimitive) value).getAsFloat());
								}
								else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
									field.set(object, ((JsonPrimitive) value).getAsDouble());
								}
								else if (type.isAssignableFrom(String.class)) {
									field.set(object, ((JsonPrimitive) value).getAsString());
								}
							}
							else if (value instanceof JsonObject) {
								field.set(object, JsonUtil.fromJsonObject((JsonObject) value, type));
							}
							else if (value instanceof JsonArray && field.getGenericType() instanceof ParameterizedType) {
								ParameterizedType pt = (ParameterizedType) field.getGenericType();
								String valueClassName = pt.getActualTypeArguments()[0].toString();
								field.set(object, JsonUtil.fromJsonArray((JsonArray) value, Class.forName(valueClassName.replace("class ", ""))));
							}
						}
						catch (IllegalArgumentException | ClassNotFoundException e) {
							JsonUtil.LOGGER.error(e.getMessage(), e);
							return null;
						}
					}
				}

				// Iterate methods with JsonKey annotation
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (MethodUtil.isSetter(method)) {
						Annotation methodAnnotation = method.getAnnotation(JsonKey.class);
						if (methodAnnotation != null) {
							JsonKey key = (JsonKey) methodAnnotation;
							try {
								Object value = jsonObject.get(key.key());
								Class<?> type = method.getParameterTypes()[0];
								if (value instanceof JsonPrimitive) {
									if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsBoolean());
									}
									else if (type.isAssignableFrom(Character.class) || type.isAssignableFrom(char.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsCharacter());
									}
									else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsByte());
									}
									else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsShort());
									}
									else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsInt());
									}
									else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsLong());
									}
									else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsFloat());
									}
									else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsDouble());
									}
									else if (type.isAssignableFrom(String.class)) {
										method.invoke(object, ((JsonPrimitive) value).getAsString());
									}
								}
								else if (value instanceof JsonObject) {
									method.invoke(object, JsonUtil.fromJsonObject((JsonObject) value, type));
								}
								else if (value instanceof JsonArray && method.getGenericReturnType() instanceof ParameterizedType) {
									ParameterizedType pt = (ParameterizedType) method.getGenericReturnType();
									String valueClassName = pt.getActualTypeArguments()[0].toString();
									method.invoke(object,
											JsonUtil.fromJsonArray((JsonArray) value, Class.forName(valueClassName.replace("class ", ""))));
								}
							}
							catch (IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
								JsonUtil.LOGGER.error(e.getMessage(), e);
							}
						}
					}
				}
				return object;
			}
			catch (InstantiationException | IllegalAccessException e) {
				JsonUtil.LOGGER.error(e.getMessage(), e);
				return null;
			}
		}
		else {
			return null;
		}
	}

	/**
	 * From json object.
	 *
	 * @param <T> the generic type
	 * @param __jsonObjectString the json object string
	 * @param __clazz the clazz
	 * @return the instance of T
	 * @throws JsonSyntaxException the json syntax exception
	 */
	public static <T> T fromJsonObject(String __jsonObjectString, Class<T> __clazz) throws JsonSyntaxException {
		JsonParser _parser = new JsonParser();
		JsonObject _jsonObject = (JsonObject) _parser.parse(__jsonObjectString);
		return JsonUtil.fromJsonObject(_jsonObject, __clazz);
	}

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
			else if (JsonUtil.isJsonObject(object.getClass())) {
				jsonArray.add(JsonUtil.toJsonObject(object));
			}
		}
		return jsonArray;
	}

	/**
	 * Parse object to json object. This object require implement @JsonObject annotation
	 *
	 * @param object the object
	 * @return the json object
	 */
	public static JsonObject toJsonObject(Object object) {
		Class<?> clazz = object.getClass();
		if (JsonUtil.isJsonObject(clazz)) {
			JsonObject jsonObject = new JsonObject();

			// Iterate fields with JsonKey annotation
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Annotation fieldAnnotation = field.getAnnotation(JsonKey.class);
				if (fieldAnnotation != null) {
					JsonKey key = (JsonKey) fieldAnnotation;
					try {
						Object value = field.get(object);

						if (value == null) {
							// Ignore this field
							// jsonObject.addProperty(key.key(), "");
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
						else if (value instanceof List<?>) {
							jsonObject.add(key.key(), JsonUtil.toJsonArray(((List<?>) value).toArray()));
						}
						else if (JsonUtil.isJsonObject(value.getClass())) {
							jsonObject.add(key.key(), JsonUtil.toJsonObject(value));
						}
						else {
							jsonObject.addProperty(key.key(), value.toString());
						}
					}
					catch (IllegalArgumentException | IllegalAccessException e) {
						JsonUtil.LOGGER.error(e.getMessage(), e);
					}
				}
			}
			// Iterate methods with JsonKey annotation
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (MethodUtil.isGetter(method)) {
					Annotation methodAnnotation = method.getAnnotation(JsonKey.class);
					if (methodAnnotation != null) {
						JsonKey key = (JsonKey) methodAnnotation;
						try {
							Object value = method.invoke(object);

							if (value == null) {
								// Ignore this method
								// jsonObject.addProperty(key.key(), "");
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
							else if (value instanceof List<?>) {
								jsonObject.add(key.key(), JsonUtil.toJsonArray(((List<?>) value).toArray()));
							}
							else if (JsonUtil.isJsonObject(value.getClass())) {
								jsonObject.add(key.key(), JsonUtil.toJsonObject(value));
							}
							else {
								jsonObject.addProperty(key.key(), value.toString());
							}
						}
						catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							JsonUtil.LOGGER.error(e.getMessage(), e);
						}
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
	 * Checks if is json object.
	 *
	 * @param clazz the clazz
	 * @return true, if is json object
	 */
	private static boolean isJsonObject(Class<?> clazz) {
		Annotation clazzAnnotation = clazz.getAnnotation(logia.utility.json.annotaion.JsonObject.class);
		if (clazzAnnotation != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
