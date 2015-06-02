package logia.utility.pool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The Class ObjectPool.
 *
 * @param <T> the generic type
 * @see {@link http://ovaraksin.blogspot.com/2013/08/simple-and-lightweight-pool.html}
 */
public abstract class ObjectPool<T> {

	/** The pool. */
	private ConcurrentLinkedQueue<T> pool;

	/** The executor service. */
	private ScheduledExecutorService executorService;

	/**
	 * Creates the pool.
	 *
	 * @param minIdle minimum number of objects residing in the pool
	 */
	public ObjectPool(final int minIdle) {
		// initialize pool
		this.initialize(minIdle);
	}

	/**
	 * Creates the pool.
	 *
	 * @param minIdle minimum number of objects residing in the pool
	 * @param maxIdle maximum number of objects residing in the pool
	 * @param validationInterval time in seconds for periodical checking of minIdle / maxIdle conditions in a separate thread. When the number of
	 *        objects is less than minIdle, missing instances will be created. When the number of objects is greater than maxIdle, too many instances
	 *        will be removed.
	 */
	public ObjectPool(final int minIdle, final int maxIdle, final long validationInterval) {
		// initialize pool
		this.initialize(minIdle);

		// check pool conditions in a separate thread
		this.executorService = Executors.newSingleThreadScheduledExecutor();
		this.executorService.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				int size = ObjectPool.this.pool.size();
				if (size < minIdle) {
					int sizeToBeAdded = minIdle - size;
					for (int i = 0; i < sizeToBeAdded; i++) {
						ObjectPool.this.pool.add(ObjectPool.this.createObject());
					}
				}
				else if (size > maxIdle) {
					int sizeToBeRemoved = size - maxIdle;
					for (int i = 0; i < sizeToBeRemoved; i++) {
						ObjectPool.this.pool.poll();
					}
				}
			}
		}, validationInterval, validationInterval, TimeUnit.SECONDS);
	}

	/**
	 * Gets the next free object from the pool. If the pool doesn't contain any objects, a new object will be created and given to the caller of this
	 * method back.
	 *
	 * @return T borrowed object
	 */
	public T borrowObject() {
		T object;
		if ((object = this.pool.poll()) == null) {
			object = this.createObject();
		}

		return object;
	}

	/**
	 * Gets the pool size.
	 *
	 * @return the pool size
	 */
	public int getPoolSize() {
		return this.pool.size();
	}

	/**
	 * Returns object back to the pool.
	 *
	 * @param object object to be returned
	 */
	public void returnObject(T object) {
		if (object == null) {
			return;
		}

		this.pool.offer(object);
	}

	/**
	 * Shutdown this pool.
	 */
	public void shutdown() {
		if (this.executorService != null) {
			this.executorService.shutdown();
		}
		if (this.pool != null) {
			this.pool.clear();
		}
	}

	/**
	 * Initialize.
	 *
	 * @param minIdle the min idle
	 */
	private void initialize(final int minIdle) {
		this.pool = new ConcurrentLinkedQueue<T>();

		for (int i = 0; i < minIdle; i++) {
			this.pool.add(this.createObject());
		}
	}

	/**
	 * Creates a new object.
	 *
	 * @return T new object
	 */
	protected abstract T createObject();
}
