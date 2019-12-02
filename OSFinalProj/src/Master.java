import java.util.ArrayList;
import java.util.Random;

public class Master {
	
	private int masterID;
	private ArrayList<Slave> slaves;
	
	Random r = new Random();
	
	public Master() {
		masterID = r.nextInt(100);
		slaves = new ArrayList<Slave>();
	}
	
	public int getMasterID() {
		return masterID;
	}
	
	//connect Slaves to the Master
	public void addSlaves(Slave s) {
		slaves.add(s);
	}
	
	
	private int getSlaveCounter(Slave s) {
		return s.getCounter();
	}
	
	//distribute Client to the Slave with the fewest current connections
	public Slave distributeClient() {
		Slave lowest = slaves.get(0);
		for (Slave s : slaves) {
			if (s.getCounter() <= lowest.getCounter()) {
				lowest = s;
			}
		}
		return lowest;
	}
}