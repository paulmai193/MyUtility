package logia.utility.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * The Class ClassUtil.
 * 
 * @author Paul Mai
 */
public class ClassUtil {

	private static final Logger LOGGER = Logger.getLogger(ClassUtil.class);

	/**
	 * Find getter.
	 *
	 * @param clazz the clazz
	 * @param field the field
	 * @return the method
	 */
	public static Method findGetter(Class<?> clazz, Field field) {
		String fieldName = field.getName();
		String methodName;
		if (field.getType().getName().equals(Boolean.class.getName())) {
			methodName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		}
		else {
			methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		}

		try {
			Method method = clazz.getMethod(methodName);
			if (MethodUtil.isGetter(method)) {
				return method;
			}
			else {
				return null;
			}
		}
		catch (NoSuchMethodException | SecurityException e) {
			ClassUtil.LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Find setter.
	 *
	 * @param clazz the clazz
	 * @param field the field
	 * @return the method
	 */
	public static Method findSetter(Class<?> clazz, Field field) {
		String fieldName = field.getName();
		String methodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		try {
			Method method = clazz.getMethod(methodName);
			if (MethodUtil.isSetter(method)) {
				return method;
			}
			else {
				return null;
			}
		}
		catch (NoSuchMethodException | SecurityException e) {
			ClassUtil.LOGGER.error(e.getMessage(), e);
			return null;
		}
	}
}
