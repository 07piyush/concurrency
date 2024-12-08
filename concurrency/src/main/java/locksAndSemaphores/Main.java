package locksAndSemaphores;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

	public static void main(String[] args) {
		/*
		 * There are two types of locks in java : 1. Implicit locks and 2. Explicity locks.
		 * 
		 * Implicit locks : monitor locks that is acquired internally by JVM. Provided by 'synchronized' block/function.
		 * 
		 * Class level implicit lock : two threads of different instance can't enter critical section.
		 * 1. synchronized on static functions (updates static variable)
		 * 2. synchronized block on ABC.class
		 * 
		 * Object-level implicit lock : each object have separate monitor lock.
		 * synchronized on non-static function ( modifies non-static class variable).
		 * synchronized block on 'this'.
		 * 
		 * Explicit locks : for more flexibility. 1. reentrant | 2. readwrite | 3. stamped | 4. semaphore
		 * 
		 * Locks do not depend on the object.
		 * Provides more flexibility with lock related operations.
		 *  
		 * 1. Reentrant lock :
		 * 		- Thread Reentrancy : a thread will not stuck in deadlock, if it tries to acquire lock again and again.
		 * 		- Explicit lock/unlock control.
		 * 		- Try locking : a thread will try acquiring lock without being blocked indefinitely.
		 * 		- Interruptible locking : Thread trying to acquire the lock can be interrupted while waiting.
		 * 		- Condition Variables.
		 * 2. ReadWrite lock.
		 * 		- Provided implementation of Shared(s) and Exclusive(x) locking.
		 * 		- taking explicit read lock can allow other threads to take read lock as well.
		 * 		- only one thread can acquire write lock and other threads are blocked to read/write.
		 * 		- may cause starvation for writer threads.
		 * 3. Stamped locking.
		 * 		- 3 types of locks : Read lock, Write lock, Optimistic lock.
		 * 		- unlike Reentrant locking, Stamped Read lock and Write lock does not allow reentrant locking.
		 * 		- Optimistic Read: a lightweight read lock that allows thread to write concurrently, and ensures consistency
		 * 						   during the read using a 'stamp' variable.
		 * 		- suitable for read heavy system.
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
		 * Prerequisite knowledge : Shared (S) lock : if taken by a thread, other threads can only take shared lock.
		 * 				  Exclusive (X) lock : if taken by a thread, no other thread can acquire any lock.
		 *  
		 * */
		ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
		ReentrantSharedResource res1 = new ReentrantSharedResource();
		
		ReentrantSharedResource res2 = new ReentrantSharedResource();
		
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
		
		//3. Stamped locking.
		/*
		 * when a lock is acquired, using an internal version is maintained which is changed when ever any write is performed
		 * on the object. 
		 * 
		 * */
		OptimisticSharedResource resource = new OptimisticSharedResource();
		
		Thread consumer = new Thread(() -> {
			for(int i =0; i<5; i++) {
				System.out.println("Consumer");
				resource.consume();
				try { Thread.sleep(50); } catch (InterruptedException ignored) {}
			}
		});
		
		Thread producer = new Thread(() -> {
			for(int i =0; i<5; i++) {
				System.out.println("Producer");
				resource.produce();
				try { Thread.sleep(50); } catch (InterruptedException ignored) {}
			}
		});
		
		consumer.start();
		producer.start();
	}

}
