package locksAndSemaphores;

import java.util.concurrent.locks.StampedLock;

public class OptimisticSharedResource {

	int available = 0;
	StampedLock stLock = new StampedLock();

	public void produce() {
		long stamp = stLock.writeLock();
		try {
			available++;
			System.out.println("Updated shared resource to : " + available);
		}
		finally {
			stLock.unlockWrite(stamp);
		}
	}
	
	public void consume() {
		long tryOptimisticRead = stLock.tryOptimisticRead();

		try {
			System.out.println("Taken read lock successfully");
			available--;
			Thread.sleep(6000);
			if(stLock.validate(tryOptimisticRead)) {
				//No writes were performed by current object since this optimistic write was acquired.
				System.out.println("Read was successful : " + available);
			}
			else {
				//Some thread has updated the shared resource after this read lock was acquired.
				//roll back.
				available++;
				System.out.println("Read roll back..");
			}
		} catch (InterruptedException e) {

		}
		finally {
			stLock.unlock(tryOptimisticRead);
		}
	}
}
