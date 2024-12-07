package daemon;

public class Main {

	public static void main(String[] args) {
		
		Thread th1 = new Thread(() -> {
			Worker worker = new Worker();
			worker.doSomething();
		});
		
		//Set th1 as daemon.
		//A daemon thread will terminate if all user threads are terminated.
		//Example : 1. JVM's garbage collector is a daemon thread, as soon as all users threads terminate, there is no
		//use of garbage collector hence it also gets terminated.
		//2. Logger thread, it also must be daemon thread, as soon as program finish or terminates, logger thread should 
		//terminate.
		
		th1.setDaemon(true);
		
		th1.start();
		
		System.out.println("Main thread completed execution.");
	}
}
