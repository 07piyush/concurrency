package producerConsumer;

public class Main {

	public static void main(String[] args) {
		
		//Since Runnable is a functional interface, so we can use lambda expression to implement run method for producer thread.
		//Format of lambda expression :  (parameters) -> { body }
		Thread producer = new Thread(() -> {
			//Runnable interface has only run() declaration, to invoke it no parameter is required.
			SharedResource items = SharedResource.getSharedResource();
			for(int i=0; i<6; i++)
				items.addItem(i);
		});
		
		Thread consumer = new Thread(() -> {
			SharedResource items = SharedResource.getSharedResource();
			System.out.println("Consumer Thread : " + Thread.currentThread().getName());
			for(int i=0; i<6; i++) {
				Integer nextItem = items.consumeItem();
				System.out.println("Consumed Item : " + nextItem);	
			}			
		});
		
		//Run producer and consumer
		producer.start();
		consumer.start();

	}

}
