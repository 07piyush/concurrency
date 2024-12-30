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
		 * 2. thenApply : when task is completed by supplyAsync then same thread will continue the work that is expected in
		 * thenApply. again the thread will belong to forkJoinPool if executor is provided.
		 * 
		 * 
		 * 
		 * */
		
	 	CompletableFuture<String> futureObj = CompletableFuture.supplyAsync(()->{
			return "John ";
		}).thenApply((String value)-> {return value + "Doe";});
		
		try {
			String result = futureObj.get();
			System.out.println("Result from completable future with then apply :" + result);
		}catch(Exception e) {}
		
		//futureObj has all the context about its thread task status etc. so then apply can be
	}

}
