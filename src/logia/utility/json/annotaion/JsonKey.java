package logia.utility.json.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation JsonKey.
 * 
 * @author Paul Mai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface JsonKey {

	/**
	 * Key.
	 *
	 * @return the string
	 */
	public String key();
}
