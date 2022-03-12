import java.util.ArrayList;
/**
 * 
 * This class has source and destination Warehouse object
 * to pickup and deliver crates. This class transfers the 
 * crates from one Warehouse to another.
 * 
 * @author Arminder Singh
 *
 */
public class Truck implements Runnable{
	private String name;
	private Warehouse source, destination;
	private int capacity;
	private ArrayList<String> crates;
	
	public Truck(Warehouse s, Warehouse d, int max) {
		this.name = "Truck "+d.getName();
		this.source = s;
		this.destination = d;
		this.capacity = max;
	}
	
	@Override
	public void run() {
		try {
			while(true && !Thread.interrupted()) {
				crates = source.pickUp(this.destination.getName(), this.capacity);
				Thread.sleep(1000);
				this.destination.deliver(crates);
				crates.removeAll(crates);
			}
		} catch (InterruptedException e) {
			System.out.println(this.name + " got interrupted");
		}finally {
			System.out.println("Truck:"+this.destination.getName() + " shutting down...");
		}
	}
	
	public String toString() {
		return this.name + this.crates;
	}
}
