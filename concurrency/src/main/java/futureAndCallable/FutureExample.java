package futureAndCallable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class FutureExample {

	public static void main(String args[]) {
		
		/*
		 * Future object is returned by ThreadPoolExecutor to manage a task submitted for execution. Information about execution
		 * of a task is updated in future object by thread pool executor.
		 * 
		 * Task submitted for execution can be of two types 1. Runnable 2. Callable. 
		 * Runnable : task does not return anything.
		 * Callable : task has a return type.
		 * 
		 * Future object can be of type based on return type of task.
		 * Future object has more information about task than just return type. hence future object itself is never null.
		 * but if we do futureObj.get, then calling thread will wait for task to complete execution, and collect the response.
		 * now futureObj can be null or non null. based on type of task.
		 * 
		 * 
		 * */
		
		int corePoolSize = 2;
		int maxPoolSize = 4;
		int keepAlive = 5;
		TimeUnit unit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> workItems = new ArrayBlockingQueue<Runnable>(2);
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		DiscardPolicy declineNew = new ThreadPoolExecutor.DiscardPolicy();
		
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAlive, unit, workItems, threadFactory, declineNew);
		
		//1. Submitted a task that will take 5 seconds to complete.
		Future<?> futureObj = poolExecutor.submit(()->{
			System.out.println("Executing a task..");
			try {
				for(int i=0; i<10; i++) {
					System.out.println(i++);
					Thread.sleep(1000);
				}
			}
			catch(Exception e) {
				//handle exception.
			}
			
		});
		
		//2. main thread will print if task was done. will return No.
		System.out.println("Is task done : " + futureObj.isDone());
		
		//3. calling thread wants to get result of above thread execution, but wait only for a threshold time.
		try {
			futureObj.get(2, TimeUnit.SECONDS);
		}catch(Exception e) {
			System.out.println("Timeout execption happend.");
		}
		
		//4. calling thread wants to get result of task executed and wait until task is completed(done/declined).
		try {
			//execution of calling thread will be blocked until below get is completed.
			//here if we collect response of futureObj.get then we will get null object. As futureObj is of type Runnable.
			futureObj.get();
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		//5. printing final status of task.
		System.out.println("is Done : " + futureObj.isDone());
		System.out.println("is Cancled" + futureObj.isCancelled());
		
		poolExecutor.shutdown();
		poolExecutor.close();
	}
}
