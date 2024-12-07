package concurrency.basic;

public class SharedResource {

	private boolean isAvailable = false;
	
	public synchronized void addItem() {
		isAvailable = true;
		notifyAll();
	}
	
	public synchronized void consumeItem() {
		//Spurious wake-up : sometimes due to system noise it might happen that without the condition being met,
		//the waiting thread gets woken up. in that case, system will behave incorrectly.
		
		//so instead of doing a if check, every time the read wakes again check if required condition is met.
		//perform business logic only if condition is met, here isAvailable is the required condition.
		while(!isAvailable) {
			System.out.println("");
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
		isAvailable = true;
	}
}
