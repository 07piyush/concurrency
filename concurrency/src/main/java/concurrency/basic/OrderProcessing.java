package concurrency.basic;

public class OrderProcessing implements Runnable{
	
	private String orderId;
	
	public OrderProcessing(String id){
		orderId = id;
	}

	@Override
	public void run(){
		System.out.println("working with order : " + orderId);
		System.out.println("Current Thread is : " + Thread.currentThread().getName());
	}
}
