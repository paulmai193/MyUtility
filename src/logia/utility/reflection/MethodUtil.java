package logia.utility.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * The Class MethodUtil.
 * 
 * @see http://tutorials.jenkov.com/java-reflection/getters-setters.html
 */
public class MethodUtil {

	/**
	 * Checks if is getter.
	 *
	 * @param method the method
	 * @return true, if is getter
	 */
	public static boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
			if (method.getName().matches("^get.*") && !method.getReturnType().equals(void.class)) {
				return true;
			}
			if (method.getName().matches("^is.*") && method.getReturnType().equals(boolean.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is setter.
	 *
	 * @param method the method
	 * @return true, if is setter
	 */
	public static boolean isSetter(Method method) {
		return Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(void.class) && method.getParameterTypes().length == 1
		        && method.getName().matches("^set.*");
	}
}
