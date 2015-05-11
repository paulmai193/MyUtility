package logia.utility.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * A factory for creating Thread pool objects.
 * 
 * @author Paul Mai
 */
public class ThreadPoolFactory {

	/**
	 * A factory for creating MyThread objects.
	 */
	public class MyThreadFactory implements java.util.concurrent.ThreadFactory {

		/** The priority. */
		private int priority;

		/**
		 * Instantiates a new my thread factory.
		 */
		public MyThreadFactory() {
			this.priority = Thread.NORM_PRIORITY;
		}

		/**
		 * Instantiates a new my thread factory.
		 *
		 * @param priority the priority of each thread
		 */
		public MyThreadFactory(int priority) {
			this.priority = priority;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setPriority(this.priority);
			return t;
		}

	}

	/** The Constant instance. */
	private static final ThreadPoolFactory instance = new ThreadPoolFactory();

	/** The thread pool. */
	private static ThreadPoolExecutor      _pool;

	/** The logger. */
	private Logger                         _logger  = Logger.getLogger(ThreadPoolFactory.class.getName());

	/**
	 * Inits the pool.
	 *
	 * @param poolSize the pool size
	 */
	public void connect() {
		ThreadPoolFactory._pool = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MyThreadFactory());
		ThreadPoolFactory._pool.setCorePoolSize(4);
		ThreadPoolFactory._pool.setMaximumPoolSize(10);
		this._logger.info("Init thread pool");
	}

	/**
	 * Connect.
	 *
	 * @param coreThreads the number of core threads
	 * @param maxThreads the max number of threads
	 * @param threadPriority the thread priority
	 */
	public void connect(int coreThreads, int maxThreads, int threadPriority) {
		ThreadPoolFactory._pool = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MyThreadFactory(threadPriority));
		ThreadPoolFactory._pool.setCorePoolSize(coreThreads);
		ThreadPoolFactory._pool.setMaximumPoolSize(maxThreads);
		this._logger.info("Init thread pool");
	}

	/**
	 * Release pool.
	 */
	public void release() {
		ThreadPoolFactory._pool.shutdown();
		int count = 0;
		while (!ThreadPoolFactory._pool.isTerminated()) {
			count++;
			if (count == 30) {
				// Force shutdown after 30 seconds
				ThreadPoolFactory._pool.shutdownNow();
			}
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {

			}
		}
		ThreadPoolFactory._pool = null;
		this._logger.info("Release thread pool");
	}

	/**
	 * Start.
	 *
	 * @param runnable the runnable
	 */
	public void start(Runnable runnable) {
		ThreadPoolFactory._pool.execute(runnable);
	}

	/**
	 * Gets the single instance of ThreadFactory.
	 *
	 * @return single instance of ThreadFactory
	 */
	public final static ThreadPoolFactory getInstance() {
		return ThreadPoolFactory.instance;
	}
}
