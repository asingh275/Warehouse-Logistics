import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * This is a class which stores crates and check whether the crates from 
 * different warehouse can be stored in the it. This class has methods 
 * pickup which adds crates to an array list and returns it and removes from
 * its storage. Other method is deliver which adds the crates passed as parameter
 * in its arraylist. 
 * 
 * @author Arminder Singh
 *
 */
public class Warehouse {
	private String name;
	private ArrayList<String> crates = new ArrayList<String>();	
	private boolean storeCrate;
	private int error = 0;
	private Lock lock;
	private Condition noCrate;
	
	public Warehouse(String n, boolean c){
		if(n == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		this.name=n;
		this.storeCrate = c;
		lock = new ReentrantLock();
		noCrate = lock.newCondition();
	}
	
	public ArrayList<String> pickUp(String destination, int max) throws InterruptedException{
		lock.lock();
		while(!(this.crates.contains(destination))) {
			Thread.sleep(500);
			noCrate.await();
		}
		ArrayList<String> order = new ArrayList<String>();
		for(int i=0;i<max;i++) {
			if(this.crates.contains(destination)) {
				order.add(destination);
				this.crates.remove(destination);
			}		
		}
		lock.unlock();
		return order;
	}
	
	public void deliver(ArrayList<String> delivery) {
		lock.lock();
		if(delivery == null) {
			throw new IllegalArgumentException("destination cannot be null");
		}
		for(String d : delivery) {
			if(!this.storeCrate && d!=this.name){
				this.error++;
			}
			this.crates.add(d);
			noCrate.signalAll();
		}
		lock.unlock();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getCrateCount() {
		return this.crates.size();
	}
	
	public String toString() {
		String result="Name: "+ this.name + " Number of stored crates: " + this.getCrateCount()+ " Number of errors: " + this.error; 
		return result;
	}
}
