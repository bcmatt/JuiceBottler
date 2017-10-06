package juicebottler;

public class FactorySpawner {

    private static final int NUM_FACTORIES = 1;

    //How long the factories Run
    public static final long PROCESSING_TIME = 5 * 1000;

 
    private static void delay(long time, String errMsg) {
        long sleepTime = Math.max(1, time);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println(errMsg);
        }
    }
    public static void main(String[] args) {
        // creates factories
        Factory[] factory = new Factory[NUM_FACTORIES];
        for (int i = 0; i < NUM_FACTORIES; i++) {
            factory[i] = new Factory("Factory #" + i);
            factory[i].startPlant();
        }

        // Give the plants time to do work
        delay(PROCESSING_TIME, "Plant malfunction");

        // Stop the plant, and wait for it to shutdown
        System.out.println("Factories stopping");
        for (Factory p : factory) {
            p.stopPlant();
        }
        for (Factory p : factory) {
            p.waitToStop();
        }

        //Gives the results
        int totalProvided = 0;
        int totalProcessed = 0;
        int totalBottles = 0;
        int totalWasted = 0;
        for (Factory a : factory) {
            totalProvided += a.getProvidedOranges();
            totalProcessed += a.getProcessedOranges();
            totalBottles += a.getBottles();
            totalWasted += a.getWaste();
        }
        
        System.out.println("Total provided/processed = " + totalProvided + "/" + totalProcessed);
        System.out.println("Created " + totalBottles
                + " bottles, wasted " + totalWasted + " oranges");
    }
}
