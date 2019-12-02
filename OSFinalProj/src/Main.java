public class Main {

	public static void main(String[] args) {
		Master master = new Master();
		
		master.addSlaves(new Slave(master));
		master.addSlaves(new Slave(master));
		master.addSlaves(new Slave(master));
		master.addSlaves(new Slave(master));
		master.addSlaves(new Slave(master));
		
		Client client1 = new Client(master);
		Client client2 = new Client(master);
		Client client3 = new Client(master);
		
		client1.requestSlave();
		client2.requestSlave();
		client3.requestSlave();
		//test adding to github
	}
}