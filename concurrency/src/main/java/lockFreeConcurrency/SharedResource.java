package lockFreeConcurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedResource {

	private int counter;
	private AtomicInteger counterCAS;
	
	SharedResource(){
		counter = 1;
		counterCAS = new AtomicInteger();
		counterCAS.set(1);
	}
	
	public void increment() {
		counter = counter + 1;
		counterCAS.addAndGet(1);
	}
	
	public void print() {
		System.out.println("Counter = " + counter);
		System.out.println("CounterCAS = " + counterCAS);
	}
}
