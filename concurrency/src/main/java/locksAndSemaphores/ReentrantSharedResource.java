package locksAndSemaphores;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantSharedResource {

	private boolean isAvailable;
	
	public ReentrantSharedResource() {
		isAvailable = false;
	}
	
	public void produce(ReentrantReadWriteLock lock) {
		lock.writeLock().lock();
		System.out.println("Producer taken exclusive lock, Thread : " + Thread.currentThread().getName());
		try {
			isAvailable = true;
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			//Handle exception
		}
		finally {
			lock.writeLock().unlock();
			System.out.println("Producer released exclusive lock, Thread : " + Thread.currentThread().getName());
		}
	}
	
	public void consume(ReentrantReadWriteLock lock) {
		lock.readLock().lock();
		System.out.println("Consumer taken shared lock, Thread : " + Thread.currentThread().getName());
		try {
			isAvailable = false;
		}
		catch(Exception e) {
			//handle
		}
		finally {
			lock.readLock().unlock();
			System.out.println("Consumer released shared lock, Thread : " + Thread.currentThread().getName());
		}
	}
}
