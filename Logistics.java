import java.util.ArrayList;
/**
 * Class containing main method to create Warehouse, FactoryWarehouse and 
 * Truck objects and then make their threads and start them. And at last
 * prints all the objects and interrupts the threads.
 * 
 * @author Arminder Singh
 *
 */
public class Logistics {

	public static void main(String[] args) {
		Warehouse w1 = new Warehouse("A", true);
		Warehouse w2 = new Warehouse("B", true);
		Warehouse w3 = new Warehouse("C", true);
		ArrayList<Warehouse> warehouses = new ArrayList<Warehouse>();
		warehouses.add(w1);
		warehouses.add(w2);
		warehouses.add(w3);
		
		FactoryWarehouse factory = new FactoryWarehouse("Factory", warehouses, 10);
		Truck t1 = new Truck(factory, w1, 2);
		Truck t2 = new Truck(factory, w2, 2);
		Truck t3 = new Truck(factory, w3, 2);
		
		Thread thread = new Thread(factory);
		Thread thread1 = new Thread(t1);
		Thread thread2 = new Thread(t2);
		Thread thread3 = new Thread(t3);
		
		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();
		
		try {
			Thread.sleep(25000);
			thread1.interrupt();
			thread2.interrupt();
			thread3.interrupt();
			thread.interrupt();
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			System.out.println(factory.toString());
			System.out.println(w1.toString());
			System.out.println(w2.toString());			
			System.out.println(w3.toString());
			System.out.println(t1.toString());
			System.out.println(t2.toString());
			System.out.println(t3.toString());
		}
	}
}
