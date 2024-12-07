package concurrency.basic;

public class Producer implements Runnable{

	private SharedResource res;
	
	public Producer(SharedResource res) {
		this.res = res;
	}
	
	@Override
	public void run() {
		System.out.println("Producer Thread : " + Thread.currentThread().getName());
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			//handle exception
		}
		res.addItem();
	}
}
