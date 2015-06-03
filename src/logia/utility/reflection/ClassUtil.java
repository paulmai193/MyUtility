package logia.utility.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtil {

	public static Method findGetter(Class<?> clazz, Field field) {
		String fieldName = field.getName();
		String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
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
			e.printStackTrace();
			return null;
		}
	}

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
			e.printStackTrace();
			return null;
		}
	}
}
