package producerConsumer;

import java.util.Deque;
import java.util.LinkedList;

public class SharedResource {

	private static SharedResource sharedResource;
	private static Deque<Integer> buffer;
	private static Integer bufferSize;
	
	private SharedResource() {
		//A singleton, same instance will be used by producer and consumer.
		buffer = new LinkedList<Integer>();
		bufferSize = 3;
	}
	
	public static SharedResource getSharedResource() {
		if(null == sharedResource) {
			synchronized(SharedResource.class) {
				if(null == sharedResource) {
					sharedResource = new SharedResource();
				}
			}
		}
		return sharedResource;
	}
	
	public synchronized void addItem(Integer item) {
		//producer will update buffer to add new items.
		while(buffer.size() >= bufferSize) {
			System.out.println("Capacity full, waiting for consumer to consume.");
			try {
				wait();
			} catch (InterruptedException e) {
				//Handle the exception.
			}
		}
		System.out.println("Produced item : " + item);
		buffer.addLast(item);
		notify();
	}
	
	public synchronized Integer consumeItem() {
		//consumer will update the buffer to poll next item.
		while(buffer.size() == 0) {
			System.out.println("Buffer is empty, waiting for Producer to produce.");
			try {
				wait();
			} catch (InterruptedException e) {
				//handle exception
			}
		}
		int value = buffer.pollFirst();
		notify();
		return value;
	}
	
}
