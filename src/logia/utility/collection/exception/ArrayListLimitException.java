package logia.utility.collection.exception;

/**
 * The Class ArrayListLimitException.
 *
 * @author Paul Mai
 */
public class ArrayListLimitException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new array list limit exception.
	 */
	public ArrayListLimitException() {
		super("Array List limited, cannot add more element");
	}

	/**
	 * Instantiates a new array list limit exception.
	 *
	 * @param __message the __message
	 * @param __cause the __cause
	 * @param __enableSuppression the __enable suppression
	 * @param __writableStackTrace the __writable stack trace
	 */
	public ArrayListLimitException(String __message, Throwable __cause, boolean __enableSuppression, boolean __writableStackTrace) {
		super(__message, __cause, __enableSuppression, __writableStackTrace);
	}

	/**
	 * Instantiates a new array list limit exception.
	 *
	 * @param __message the __message
	 * @param __cause the __cause
	 */
	public ArrayListLimitException(String __message, Throwable __cause) {
		super(__message, __cause);
	}

	/**
	 * Instantiates a new array list limit exception.
	 *
	 * @param __message the __message
	 */
	public ArrayListLimitException(String __message) {
		super(__message);
	}

	/**
	 * Instantiates a new array list limit exception.
	 *
	 * @param __cause the __cause
	 */
	public ArrayListLimitException(Throwable __cause) {
		super(__cause);
	}

}
