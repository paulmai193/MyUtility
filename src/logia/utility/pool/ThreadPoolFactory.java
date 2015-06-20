package logia.utility.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
	private class MyThreadFactory implements java.util.concurrent.ThreadFactory {

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
	@Deprecated
	private static final ThreadPoolFactory instance = new ThreadPoolFactory();

	/** The thread pool. */
	private ThreadPoolExecutor             _pool;

	/** The logger. */
	private Logger                         _logger  = Logger.getLogger(ThreadPoolFactory.class.getName());

	/**
	 * Instantiates a new thread pool factory with default 1 thread in pool.
	 */
	public ThreadPoolFactory() {
		this._pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, new MyThreadFactory());
		this._logger.info("Init thread pool");
	}

	/**
	 * Instantiates a new thread pool factory.
	 *
	 * @param nThreads the number threads in pool
	 */
	public ThreadPoolFactory(int nThreads) {
		this._pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads, new MyThreadFactory());
		this._logger.info("Init thread pool");
	}

	/**
	 * Instantiates a new thread pool factory.
	 *
	 * @param nThreads the number threads in pool
	 * @param threadPriority the thread priority
	 */
	public ThreadPoolFactory(int nThreads, int threadPriority) {
		this._pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads, new MyThreadFactory(threadPriority));
		this._logger.info("Init thread pool");
	}

	/**
	 * Instantiates a new thread pool factory.
	 *
	 * @param coreThreads the core threads
	 * @param maxThreads the max threads
	 * @param threadPriority the thread priority
	 * @param isCached the is cached
	 */
	public ThreadPoolFactory(int coreThreads, int maxThreads, int threadPriority, boolean isCached) {
		this._pool = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MyThreadFactory(threadPriority));
		this._pool.setCorePoolSize(coreThreads);
		this._pool.setMaximumPoolSize(maxThreads);
		if (!isCached) {
			this._pool.setKeepAliveTime(0, TimeUnit.MILLISECONDS);
		}
		this._logger.info("Init thread pool");
	}

	/**
	 * Inits the pool.
	 */
	@Deprecated
	public void connect() {
		this._pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, new MyThreadFactory());
		this._logger.info("Init thread pool");
	}

	/**
	 * Connect.
	 *
	 * @param nThreads the number threads in pool
	 */
	@Deprecated
	public void connect(int nThreads) {
		this._pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads, new MyThreadFactory());
		this._logger.info("Init thread pool");
	}

	/**
	 * Connect.
	 *
	 * @param nThreads the number threads in pool
	 * @param threadPriority the thread priority
	 */
	@Deprecated
	public void connect(int nThreads, int threadPriority) {
		this._pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads, new MyThreadFactory(threadPriority));
		this._logger.info("Init thread pool");
	}

	/**
	 * Connect.
	 *
	 * @param coreThreads the number of core threads
	 * @param maxThreads the max number of threads
	 * @param threadPriority the thread priority
	 * @param isCached the is cached
	 */
	@Deprecated
	public void connect(int coreThreads, int maxThreads, int threadPriority, boolean isCached) {
		this._pool = (ThreadPoolExecutor) Executors.newCachedThreadPool(new MyThreadFactory(threadPriority));
		this._pool.setCorePoolSize(coreThreads);
		this._pool.setMaximumPoolSize(maxThreads);
		if (!isCached) {
			this._pool.setKeepAliveTime(0, TimeUnit.MILLISECONDS);
		}
		this._logger.info("Init thread pool");
	}

	/**
	 * Gets the active count.
	 *
	 * @return the active count
	 */
	public int getActiveCount() {
		return this._pool.getActiveCount();
	}

	/**
	 * Gets the completed task count.
	 *
	 * @return the completed task count
	 */
	public long getCompletedTaskCount() {
		return this._pool.getCompletedTaskCount();
	}

	/**
	 * Gets the task count.
	 *
	 * @return the task count
	 */
	public long getTaskCount() {
		return this._pool.getTaskCount();
	}

	/**
	 * Release pool.
	 */
	public void release() {
		this._pool.shutdown();
		int count = 0;
		while (!this._pool.isTerminated()) {
			count++;
			if (count == 30) {
				// Force shutdown after 30 seconds
				this._pool.shutdownNow();
			}
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {

			}
		}
		this._pool = null;
		this._logger.info("Release thread pool");
	}

	/**
	 * Start.
	 *
	 * @param runnable the runnable
	 */
	public void start(Runnable runnable) {
		this._pool.execute(runnable);
	}

	/**
	 * Gets the single instance of ThreadFactory.
	 *
	 * @return single instance of ThreadFactory
	 */
	@Deprecated
	public final static ThreadPoolFactory getInstance() {
		return ThreadPoolFactory.instance;
	}
}
