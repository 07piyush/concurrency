package futureAndCallable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

public class CompletableFutureExample {

	public static void main(String[] args) {
		/*
		 * Introduced in java 8. CompletableFuture provide chaining of Async tasks without blocking calling thread.
		 * 
		 * Using the APIs : supplyAsync, thenApply, thenApplyAsync. we can chain a series of operations which can be executed
		 * in parallel with calling thread. like multiple logics are building a string one by one and in the mean time calling
		 * thread is busy with its other independent tasks.
		 * 
		 * CompletableFuture is interface that extends Future. hence all functionality of future are there.
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
		
		/*
		 * 1. supplyAsync. : we can provide a task to this API, and it executes the task by a thread from forkJoinPool.
		 * 
		 * if we need to keep execution in a controlled enviornment, then an executor can be passed. Then thread from provided
		 * thread pool will be picked for executing submitted task.
		 * 
		 * It will return a CompletableFuture object.
		 * 
		 * 2. thenApply : when task is completed by supplyAsync then same thread will continue the work that is expected in
		 * thenApply. again the thread will belong to forkJoinPool if executor is provided.
		 * 
		 * futureObj has all the context about its thread task status etc. so thenApply or thenApplyAsync registers a callback. 
		 * This callback specifies what must happen once result sypplyAsync is available.
		 * 
		 * 3. thenApplylAsync : the task will be performed by some other thread. again thread will be chosen on type of executor, forkjoinpool if no
		 * executor provided.
		 * 
		 *  Why there is thenApply and thenApplyAsync? what is the usecase of both?
		 *  Ans : since thenApply use same thread, there is no context switching, so lightweight task can be done using thenapply.
		 *  while if a task is CPU-intensive then use thenApplyAsync to increase responsiveness in some cases.
		 *  
		 * */
		
	 	CompletableFuture<String> futureObj = CompletableFuture.supplyAsync(()->{
			return "John ";
		}).thenApply((String value)-> {return value + "Doe";});
		
		try {
			String result = futureObj.get();
			System.out.println("Result from completable future with then apply :" + result);
		}catch(Exception e) {}
		
		
		//below is example of thenApplyAsync.
		CompletableFuture<String> futureObj2 = CompletableFuture.supplyAsync(()->{
			return "John ";
		}).thenApplyAsync((String value)-> {return value + "Doe";});
		
		try {
			String result = futureObj2.get();
			System.out.println("Result from completable future with then apply :" + result);
		}catch(Exception e) {}
		
		
		
	}

}
