package logia.utility.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodUtil {

	public static boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
			if (method.getName().matches("^get[A-Z].*") && !method.getReturnType().equals(void.class))
				return true;
			if (method.getName().matches("^is[A-Z].*") && method.getReturnType().equals(boolean.class))
				return true;
		}
		return false;
	}

	public static boolean isSetter(Method method) {
		return Modifier.isPublic(method.getModifiers()) && method.getReturnType().equals(void.class) && method.getParameterTypes().length == 1
		        && method.getName().matches("^set[A-Z].*");
	}
}
