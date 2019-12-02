import java.util.ArrayList;
import java.util.Random;

public class Slave {

		private Master master;
		private int slaveID;
		private int numClients;
		private ArrayList<Client> clients;
		
		Random r = new Random();
		
		public Slave(Master m) {
			master= m;
			slaveID = r.nextInt(100);
			clients = new ArrayList<Client>();
		}
		
		//increment the counter every time a Client is connected
		public void newClient(Client c) {
			clients.add(c);
			numClients++;
		}
		
		//when the client disconnects, the counter decrements
		public void removeClient(Client c) {
			clients.remove(c);
			numClients--;
		}
		
		//Master has access to the counter of the slaves
		public int getCounter() {
			return numClients;
		}
		
		
		
}