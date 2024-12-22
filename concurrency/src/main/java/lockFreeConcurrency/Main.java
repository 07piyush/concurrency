package lockFreeConcurrency;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		SharedResource resource = new SharedResource();
		
		Thread t1 = new Thread(() -> {
			for(int i=0; i<100; i++)
				resource.increment();
		});
		
		Thread t2 = new Thread(() -> {
			for(int i=0; i<100; i++)
				resource.increment();
		});
		
		t1.start();
		t2.start();
		
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resource.print();
	}

}
