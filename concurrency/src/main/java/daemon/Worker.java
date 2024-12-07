package daemon;

public class Worker {

	public void doSomething() {
		System.out.println("working...");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			//Handle exception
		}
		System.out.println("Completed work!");
	}
}
