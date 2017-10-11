package juicebottler;

//worker thread
public class Worker extends Thread {
	// used to assign a created factory for this worker
	private Factory createdfactory;

	public Worker(Factory factory) {
		createdfactory = factory;
	}

	public void run() {
		System.out.print(Thread.currentThread().getName() + " Juicing ");

		while (createdfactory.gettowork) {
			// If the Assembly line is empty go get a new orange
			// Majority of these two blocks from stackoverflow user Jeffrey
			// https://stackoverflow.com/questions11165852/java-singlton-and-synchronization
			synchronized (createdfactory.assemblyline) {
				if (createdfactory.assemblyline.isEmpty()) {
					createdfactory.assemblyline.add(new Orange());
					createdfactory.orangesProvided++;
				}
			}
			Orange orange;
			// processing an orange
			// Check if there are any oranges on the assembly line
			synchronized (createdfactory.assemblyline) {
				if (createdfactory.assemblyline.size() > 0) {
					orange = createdfactory.assemblyline.remove();
				} else {
					continue;
				}

			}
			if (orange.getState() != Orange.State.Processed) {
				orange.runProcess();
				createdfactory.assemblyline.add(orange);
			} else {
				createdfactory.orangesProcessed++;
			}
		}
	}
}