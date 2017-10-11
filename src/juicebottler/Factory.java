package juicebottler;

import java.util.Queue;
import java.util.ArrayDeque;


public class Factory {
	//Number of workers each factory can hold (they're very small)
	private final int FACTORY_CAPACITY = 2;
	//Number of Oranges needed to fill a bottle.
    private final int ORANGES_IN_BOTTLE = 3;
    //Keeps track of the factories efficiency
    public int orangesProvided;
    public int orangesProcessed;
    //Used to let the factories know when it's time to work
    protected volatile boolean gettowork;
    //Priority Queue use from stackoverflow user Jon Skeet https//stackoverflow.com/questions/683041/how-do-i-use-a-priority-queue 
    public final Queue<Orange> assemblyline = new ArrayDeque<>(FACTORY_CAPACITY);
 
    //Workers very similar to your factory creation
    private Worker[] workers;
    //Factories, now complete with workers.
	Factory(String Juicer) {
        orangesProvided = 0;
        orangesProcessed = 0;
        workers = new Worker[FACTORY_CAPACITY];
        for (int i = 0; i < FACTORY_CAPACITY; i++) {
            workers[i] = new Worker(this);
        }
    }
	    
    //Starts the plant
    public void startPlant() {
        gettowork = true;
        //Starts the workers
        //added for loop to create workers from array
        for (Worker workers : workers) {
          workers.start();    
         }
    }
    public void waitToStop() {
    for (Worker workers : workers) {    
    	try {
                workers.join();
            } catch (InterruptedException ex) {
                System.err.println(workers.getName() + " stop malfunction");
         }
    	}
    }

    //tells the plants they can shut down for now
    public void stopPlant() {
        gettowork = false;
    }
    //the total number of oranges that were fetched while the factory was running
    public int getProvidedOranges() {
        return orangesProvided;
    }

    public int getProcessedOranges() {
        return orangesProcessed;
    }
    //Tells how many bottles of orange juice you made
    public int getBottles() {
        return orangesProcessed / ORANGES_IN_BOTTLE;
    }

    public int getWaste() {
        return orangesProcessed % ORANGES_IN_BOTTLE;
    }
}