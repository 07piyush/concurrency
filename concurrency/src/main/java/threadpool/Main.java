package threadpool;

public class Main {

	public static void main(String[] args) {
		
		MyThreadPoolImpl executor = new MyThreadPoolImpl();
		/*
		 * The threadpool is configured with 2 core pool size, 3 max pool size and 2 task queue size.
		 * so when 4 tasks are submitted, then 2 threads are consumed to execute 2 tasks. while remaining
		 * 2 tasks are put in wait queue.
		 */
//		executor.submitFourTasks();
		
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
		}
		/*when 8 tasks are submitted, then task1, task2 are executed by thread1, thread2.
		 * task3, task4 are put in queue.
		 * task5 is executed with thread3.
		 * task6, task7, task8 are rejected.
		 * 
		 * */
		executor.submitEightTasks();
	}
		
}
