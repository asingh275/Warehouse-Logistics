import java.util.ArrayList;
/**
 * This is a subclass of the Warehouse class which produces the
 * sufficient crates for the warehouses passed in the constructor.
 * This class produces and stores crates in its data member.
 *  
 * @author Arminder Singh
 *
 */
public class FactoryWarehouse extends Warehouse implements Runnable{
	private ArrayList<Warehouse> destinations;
	private int cratesForWarehouse;
	private int start = 0;
	
	public FactoryWarehouse(String name, ArrayList<Warehouse> d, int c) {
		super(name, true);
		
		if(d == null) {
			throw new IllegalArgumentException("Arraylist of destinations cannot be null");
		}
		
		this.destinations=d;
		this.cratesForWarehouse = c;
	}

	@Override
	public void run() {
		ArrayList<String> added = new ArrayList<String>();
		try {
			while( start < cratesForWarehouse*destinations.size()) {
				for(Warehouse d :destinations) {
					int count=0;
					int toBeAdded = 3;
					for(String a : added) {
						if(a==d.getName()) {
							count++;
						}
					}
					if(cratesForWarehouse-count < 3) {
						toBeAdded = cratesForWarehouse-count;
					}
					ArrayList<String> x = new ArrayList<String>();
					for(int i=0;i<toBeAdded;i++) {
						added.add(d.getName());
						x.add(d.getName());
						start++;
					}
					this.deliver(x);
				}
				Thread.sleep(5000);
			}
		}catch (InterruptedException e) {
			System.out.println("Factory got interrupted");
		}finally {
			System.out.println("Factory shutting down....");
		}
	}
	
	public String toString() {
		return "Factory Name: "+this.getName() + " Stored crates: " + this.getCrateCount() + " Produced crates: " + this.start;
	}
}
