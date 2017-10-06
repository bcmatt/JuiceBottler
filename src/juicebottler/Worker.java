package juicebottler;

//worker thread
public class Worker extends Thread {
	//used to assign a created factory for this worker
  private Factory createdfactory;

  public Worker(Factory factory, String juice) {
      createdfactory = factory;
      setName(juice);
  }
  public void run() {
      System.out.print(Thread.currentThread().getName() + " Juicing ");
          // If the Assembly line is empty go get a new orange
          //Majority of these two blocks from stackoverflow user Jeffrey https://stackoverflow.com/questions11165852/java-singlton-and-synchronization
              if (createdfactory.assemblyline.isEmpty()) {
                  createdfactory.assemblyline.add(new Orange());
                  createdfactory.orangesProvided++;
              }
          // processing an orange 
          Orange orange;
              // Check if there are any oranges on the assembly line
              if (createdfactory.assemblyline != null) {
                  orange = createdfactory.assemblyline.remove();
              } else {
                  continue;
              }
          
          if (orange.getState() != Orange.State.Processed) {
              orange.runProcess();
              createdfactory.assemblyline.add(orange);
          } else {
              createdfactory.orangesProcessed++;
          }
      
  }
}