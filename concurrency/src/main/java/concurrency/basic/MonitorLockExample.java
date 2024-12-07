package concurrency.basic;

public class MonitorLockExample {

	int count = 0;
	
	public synchronized void task1() {
		System.out.println("Inside task1");
		try {
			count++;
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			//required exception handling
		}
		System.out.println("Exiting task1");
	}
	
	public void task2() {
		count--;
		System.out.println("inside task2");
		synchronized(this) {
			System.out.println("Executing task2");
		}
	}
	
	public void task3() {
		System.out.println("Executing task3");
	}
}
