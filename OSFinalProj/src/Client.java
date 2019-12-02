import java.util.Random;

public class Client {
	private int clientID;
	private Slave slave;
	private Master master;
	
	Random random = new Random();
	
	public Client(Master m) {
		clientID = random.nextInt(100);
		master = m;
	}
	public void requestSlave() {
		slave = master.distributeClient();
	}
	

}