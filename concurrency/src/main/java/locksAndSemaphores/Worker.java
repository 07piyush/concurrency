package locksAndSemaphores;

import java.util.concurrent.locks.ReentrantLock;

public class Worker {
	
	int sharedResource;
	
	public Worker() {
		sharedResource = 0;
	}

	public void doSomething(ReentrantLock lock) {
		try {
			lock.lock();
			System.out.println("Lock acquired by Thread : " + Thread.currentThread().getName());
			Thread.sleep(5000);
			//use sharedResource.
		}catch (Exception e) {
			//Handle exception.
		}
		finally {
			lock.unlock();
			System.out.println("Lock released by Thread : " + Thread.currentThread().getName());
		}
	}
}
