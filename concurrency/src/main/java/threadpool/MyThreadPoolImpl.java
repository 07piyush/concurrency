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
	int corePoolSize = 2;
	
	/*
	 * maximumPoolSize : when all threads out of corePoolSize are busy and workQueue is also full 
	 * and a new task is submitted for execution, then a new thread will be created. In this case 2 more 
	 * threads can be created summing up to 5.
	 * */  
	int maximumPoolSize = 3;

	/*
	 * keepAliveTime when the number of threads is greater than the core, this is the maximum time that excess idle 
	 * threads will wait for new tasks before terminating.
	 * */
	long keepAliveTime = 1;

	TimeUnit unit = TimeUnit.MINUTES;

	/*
	 * this queue will hold the runnable task needs to be executed when a thread becomes available.
	 * */
	BlockingQueue <Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);

	/*
	 * whenever during the life cycle of thread pool executor a new thread is created, some properties of thread can
	 * possibly be needed in certain way like daemon thread or priority of thread etc.
	 * ThreadPoolExecutor will use Factory to create new threads. 
	 * */
	CustomThreadFactory threadFactory = new CustomThreadFactory();

	/*
	 * RejectedExecutionHandle describes when a task is submitted to execute, but neither there is any idle thread,
	 * nor there is occupancy in queue. also any max capacity thread is also consumed and unavailable. This scenario is known
	 * as Saturation. Saturation policy define how executor will handle tasks that are submitted at saturation point.
	 * 
	 * 1. Abort policy. : new ThreadPoolExecutor.AbortPolicy() | executor will throw RejectedExecutionException.
	 * 2. Caller-Runs Policy. : task is not rejected, instead it is executed by caller thread. (lower parallelism)
	 * 3. Discard policy : silently discards the new task when it fails to submit.
	 * 4. Discard oldest policy : removes the oldest task from the queue(first) and adds newest task in it. and oldest task is rejected.
	 * 5. Custom rejection policy : to handle the rejected task manually, we can implement RejectedExecutionHandler.
	 * 
	 * Executor will call rejectedExecutor which is a method declared in the interface. This method can be overridden
	 * to add logs etc.
	 * 
	 * */
	RejectedExecutionHandler handler = new CustomRejectedExecutionHandler();
	
	ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	
	public MyThreadPoolImpl() {
		//the keepAliveTime is only usefull when below is checked true.
		threadPool.allowCoreThreadTimeOut(true);
	}
	
	public void submitFourTasks() {
		System.out.println("Demonstration with 4 concurrent task submissions.");
		for(int i=0; i<4; i++) {
			
			threadPool.submit(() -> {
				System.out.println("Running task by Thread : " + Thread.currentThread().getName());
				try {
					Thread.sleep(5000);
				}
				catch (Exception e) {
					System.out.println(e.getStackTrace());
				}
				
			});
		}
		threadPool.shutdown();

	}
	
	public void submitEightTasks() {
		System.out.println("Demonstration with 8 concurrent task submissions.");
		for(int i=0; i<8; i++) {
			
			threadPool.submit(() -> {
				System.out.println("Running task by Thread : " + Thread.currentThread().getName());
				try {
					Thread.sleep(5000);
				}
				catch (Exception e) {
					
				}
			});
		}
		threadPool.shutdown();
	}
}

class CustomThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		Thread th = new Thread(r);
		th.setPriority(Thread.NORM_PRIORITY);
		th.setDaemon(false);
		return th;
	}
	
}

class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("Rejected task by Thread : ." + Thread.currentThread().getName());
	}
	
}
