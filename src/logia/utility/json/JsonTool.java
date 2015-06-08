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
	 * From json array.
	 *
	 * @param <T> the generic type
	 * @param jsonArray the json array
	 * @param clazz the clazz
	 * @return the list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> fromJsonArray(JsonArray jsonArray, Class<T> clazz) {
		List list = new ArrayList<>();
		for (JsonElement element : jsonArray) {
			if (element instanceof JsonPrimitive) {
				if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class)) {
					list.add(element.getAsBoolean());
				}
				else if (clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(char.class)) {
					list.add(element.getAsCharacter());
				}
				else if (clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(byte.class)) {
					list.add(element.getAsByte());
				}
				else if (clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class)) {
					list.add(element.getAsShort());
				}
				else if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)) {
					list.add(element.getAsInt());
				}
				else if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)) {
					list.add(element.getAsLong());
				}
				else if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class)) {
					list.add(element.getAsFloat());
				}
				else if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)) {
					list.add(element.getAsDouble());
				}
				else if (clazz.isAssignableFrom(String.class)) {
					list.add(element.getAsString());
				}
			}
			else if (element instanceof JsonObject) {
				list.add(JsonTool.fromJsonObject((JsonObject) element, clazz));
			}
		}
		return list;
	}

	/**
	 * From json object.
	 *
	 * @param <T> the generic type
	 * @param jsonObject the json object
	 * @param clazz the clazz
	 * @return the t
	 */
	public static <T> T fromJsonObject(JsonObject jsonObject, Class<T> clazz) {
		if (JsonTool.isJsonObject(clazz)) {
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
								field.set(object, JsonTool.fromJsonObject((JsonObject) value, type));
							}
							else if (value instanceof JsonArray && field.getGenericType() instanceof ParameterizedType) {
								ParameterizedType pt = (ParameterizedType) field.getGenericType();
								String valueClassName = pt.getActualTypeArguments()[0].toString();
								field.set(object, JsonTool.fromJsonArray((JsonArray) value, Class.forName(valueClassName.replace("class ", ""))));
							}
						}
						catch (IllegalArgumentException | ClassNotFoundException e) {
							e.printStackTrace();
							return null;
						}
					}
				}

				// Iterate fields with JsonKey annotation
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
									method.invoke(object, JsonTool.fromJsonObject((JsonObject) value, type));
								}
								else if (value instanceof JsonArray && method.getGenericReturnType() instanceof ParameterizedType) {
									ParameterizedType pt = (ParameterizedType) method.getGenericReturnType();
									String valueClassName = pt.getActualTypeArguments()[0].toString();
									method.invoke(object,
									        JsonTool.fromJsonArray((JsonArray) value, Class.forName(valueClassName.replace("class ", ""))));
								}
							}
							catch (IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
				return object;
			}
			catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
		else {
			return null;
		}
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
			else if (JsonTool.isJsonObject(object.getClass())) {
				jsonArray.add(JsonTool.toJsonObject(object));
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
		if (JsonTool.isJsonObject(clazz)) {
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
						else if (value instanceof List<?>) {
							jsonObject.add(key.key(), JsonTool.toJsonArray(((List<?>) value).toArray()));
						}
						else if (JsonTool.isJsonObject(value.getClass())) {
							jsonObject.add(key.key(), JsonTool.toJsonObject(value));
						}
						else {
							jsonObject.addProperty(key.key(), value.toString());
						}
					}
					catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
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
							else if (value instanceof List<?>) {
								jsonObject.add(key.key(), JsonTool.toJsonArray(((List<?>) value).toArray()));
							}
							else if (JsonTool.isJsonObject(value.getClass())) {
								jsonObject.add(key.key(), JsonTool.toJsonObject(value));
							}
							else {
								jsonObject.addProperty(key.key(), value.toString());
							}
						}
						catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
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
