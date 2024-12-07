package locksAndSemaphores;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

	public static void main(String[] args) {
		/*
		 * synchronized blocks depending on object will allow entry in critical section or not. It is object dependent.
		 * 
		 * Locks do not depend on the object.
		 * 
		 * Types of locks : 
		 * 1. Reentrant lock.
		 * 2. ReadWrite lock.
		 * 3. Semaphore
		 * 4. 
		 * 
		 * 
		 * */
		
		//1. ReentrantLock
		ReentrantLock lock = new ReentrantLock();
		
		Thread th1 = new Thread(() -> {
			Worker worker1 = new Worker();
			worker1.doSomething(lock);
		});
		
		Thread th2 = new Thread(() -> {
			Worker worker2 = new Worker();
			worker2.doSomething(lock);
		});
		
		//Despite having separate objects no two threads will be able to 
		//simultaneously access code in doSomething.  
		th1.start();
		th2.start();
		
		//2. ReadWriteLock
		/*
		 * ReadLock : More than 1 thread can acquire read lock.
		 * WriteLock : only 1 thread can acquire write lock.
		 * 
		 * Prerequisite : Shared (S) lock : if taken by a thread, other threads can only take shared lock.
		 * 				  Exclusive (X) lock : if taken by a thread, no other thread can acquire any lock.
		 *  
		 * */
		ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
		SharedResource res1 = new SharedResource();
		
		SharedResource res2 = new SharedResource();
		
		Thread t1 = new Thread(() -> {
			res1.produce(rwLock);
		});
		
		Thread t2 = new Thread(() -> {
			res1.produce(rwLock);
		});
		
		//Despite being different object.
		Thread t3 = new Thread(() -> {
			res2.consume(rwLock);
		});
		
		Thread t4 = new Thread(() -> {
			res2.produce(rwLock);
		});
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
	}

}
