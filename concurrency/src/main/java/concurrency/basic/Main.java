package concurrency.basic;

public class Main {

	public static void main(String[] args) {
		//1. There are two ways to create a thread.
			//a. extending Thread class.
			//b. implementing Runnable.

		OrderProcessing runnableObject = new OrderProcessing("order1");
		Thread thread = new Thread(runnableObject);
		System.out.println("Current Thread is : " + Thread.currentThread().getName());
		System.out.println("Created thread object of runnable object.");
		//start method internally use run method, and thread will execute whatever is implemented in overridden run
		//of runnableObject.
		thread.start();
		System.out.println("Executing remaining of : " + Thread.currentThread().getName());
		
		//2. Monitor locks.
			//using Synchronized keyword, either function or a block is synchronized.
			//a lock is taken at object level, and if two threads being of same object, try to accessing synchronized block
			// then second is blocked until work is done by first.
		MonitorLockExample obj = new MonitorLockExample();
		//Since all three threads working on same object, so monitor lock takes in place for synchronized sections.
		Thread t1 = new Thread(() -> {obj.task1();});
		Thread t2 = new Thread(() -> {obj.task2();});
		Thread t3 = new Thread(() -> {obj.task3();});
		
		t1.start();
		t2.start();
		t3.start();
		
		//3. Spurious wake-up
		SharedResource resourceObj = new SharedResource();
		Thread producer = new Thread(new Producer(resourceObj));
		Thread consumer =  new Thread(new Consumer(resourceObj));
		
		//Thread is in Runnable state.
		producer.start();
		consumer.start();
	}

}
