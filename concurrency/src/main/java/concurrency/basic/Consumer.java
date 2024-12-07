package concurrency.basic;

public class Consumer implements Runnable{

	private SharedResource res;
	
	public Consumer(SharedResource res) {
		this.res = res;
	}
	
	@Override
	public void run() {
		System.out.println("Consumer thread : " + Thread.currentThread().getName());
		res.consumeItem();
	}
}
