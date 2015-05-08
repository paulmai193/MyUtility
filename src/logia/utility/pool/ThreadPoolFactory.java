package logia.utility.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	private static ExecutorService         _pool;

	/** The logger. */
	private Logger                         _logger  = Logger.getLogger(ThreadPoolFactory.class.getName());

	/**
	 * Inits the pool.
	 *
	 * @param poolSize the pool size
	 */
	public void connect() {
		ThreadPoolFactory._pool = Executors.newFixedThreadPool(10, new MyThreadFactory());
		this._logger.info("Init thread pool");
	}

	/**
	 * Connect.
	 *
	 * @param numThreads the num threads
	 * @param threadPriority the thread priority
	 */
	public void connect(int numThreads, int threadPriority) {
		ThreadPoolFactory._pool = Executors.newFixedThreadPool(numThreads, new MyThreadFactory(threadPriority));
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
