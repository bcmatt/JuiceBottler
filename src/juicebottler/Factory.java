package juicebottler;

import java.util.Queue;
import java.util.PriorityQueue;

public class Factory {
	//Number of workers each factory can hold (they're very small)
	public final int FACTORY_CAPACITY = 2;
	//Number of Oranges needed to fill a bottle.
    public final int ORANGES_IN_BOTTLE = 3;
    //Keeps track of the factories efficiency
    public int orangesProvided;
    public int orangesProcessed;
    //Used to let the factories know when it's time to work
    protected volatile boolean gettowork;
    //Priority Queue use from stackoverflow user Jon Skeet https//stackoverflow.com/questions/683041/how-do-i-use-a-priority-queue 
    public final Queue<Orange> assemblyline = new PriorityQueue<>(FACTORY_CAPACITY);
    //Factory
    Factory() {
        this("Factory");
    }
    //Workers
    private Worker[] workers;
    //Factories, complete with workers.
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
          worker.start();    
    }
    public void waitToStop() {
            try {
                worker.join();
            } catch (InterruptedException ex) {
                System.err.println(worker.getName() + " stop malfunction");
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