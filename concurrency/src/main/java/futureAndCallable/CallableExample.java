package futureAndCallable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class CallableExample {

	public static void main(String args[]) {
		
		int coreThreadsCount = 2;
		int maxThreadsCount = 4;
		int keepAlive = 3;
		TimeUnit unit = TimeUnit.SECONDS;

		BlockingQueue<Runnable> workItems = new ArrayBlockingQueue<>(2);

		DiscardPolicy discardPolicy = new DiscardPolicy();
		
		ThreadFactory defaultFactory = Executors.defaultThreadFactory();

		ThreadPoolExecutor executor = new ThreadPoolExecutor(coreThreadsCount, maxThreadsCount, keepAlive, unit, workItems, defaultFactory, discardPolicy);
		
		//Ask thread pool executor to perform a task in parallel.
		Future<Integer> futureObj = executor.submit(()->{
			int i = 0;
			try {
				for(; i<10; i++) {
					System.out.println(i);
					Thread.sleep(1000);
				}
			}
			catch(Exception e) {
			}
			return i;
		});
		
		//caller thread doing some other independent work of its own.
		try {
			System.out.println("main thread busy...");
			Thread.sleep(2000);
		}catch(Exception e) {}
		
		//caller thread now needs value of task performed by executor associated to a futureObj.
		try {
			Integer result = futureObj.get();
			System.out.println("From main got futureObj result : " + result);
		}catch(Exception e) {}
		
		
		
		executor.close();
	}
}
