/*
 *	The class that will test your TreeLock. The TAs will run our own tests against your
 *	handin, but be sure to implement this class to test your program.
 */
class TestTreeLock {

	// Change this to change the number of threads in the system.
	private static final int numThr = 4;
	

	public static void main(String args[]) throws InterruptedException {
		// Counter ctr = new Counter(numThr);

       /**  Write code to test the tree lock implementation here.
         *   1. Instantiate an array of Thread objects
         *   2. Initialize each thread with the Runnable counter object, and call the thread's start() method
         *   3. Be sure to wait until all threads join before printing the final count 
         **/
		Thread threads[] = new Thread[numThr];
		int numOfTest = 2000;
		int numOfErr = 0;
		for (int c = 0; c < numOfTest; c++) {
			Counter ctr = new Counter(numThr);
			System.out.println("Start Test @" + c);
			for (int i = 0; i < numThr; i++) {
				threads[i] = new Thread(ctr);
				threads[i].run(); //Run the threads
			}
	
			for (Thread t : threads) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (ctr.getCount() != 1000000) {
				numOfErr += 1;
				System.out.println("Error! Counter is " + ctr.getCount());
			}
		}
		System.out.printf("%d errors out of %d Tests", numOfErr, numOfTest);
	}
}
