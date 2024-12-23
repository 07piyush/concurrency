package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MyThreadPoolImpl {
	/*
	 * corePoolSize : the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
	 * */
	int corePoolSize = 3;
	
	/*
	 * maximumPoolSize : when all threads out of corePoolSize are busy and workQueue is also full 
	 * and a new task is submitted for execution, then a new thread will be created. In this case 2 more 
	 * threads can be created summing up to 5.
	 * */  
	int maximumPoolSize = 5;

	/*
	 * keepAliveTime when the number of threads is greater than the core, this is the maximum time that excess idle 
	 * threads will wait for new tasks before terminating.
	 * */
	long keepAliveTime = 10;

	TimeUnit unit = TimeUnit.MINUTES;

	/*
	 * this queue will hold the runnable task needs to be executed when a thread becomes available.
	 * */
	BlockingQueue <Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5);

	/*
	 * whenever during the life cycle of thread pool executor a new thread is created, some properties of thread can
	 * possibly be needed in certain way like daemon thread or priority of thread etc.
	 * ThreadPoolExecutor will use Factory to create new threads. 
	 * */
	CustomThreadFactory threadFactory = new CustomThreadFactory();

	/*
	 * RejectedExecutionHandle describes when a task is submitted to execute, but neither there is any idle thread,
	 * nor there is occupancy in queue. also any max capacity thread is also consumed and unavailable.
	 * 
	 * Executor will call rejectedExecutor which is a method declared in the interface. This method can be overridden
	 * to add logs etc.
	 * 
	 * */
	RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
	
	ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, null);

}

class CustomThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		Thread th = new Thread(r);
		th.setPriority(Thread.NORM_PRIORITY);
		th.setDaemon(false);
		return null;
	}
	
}
