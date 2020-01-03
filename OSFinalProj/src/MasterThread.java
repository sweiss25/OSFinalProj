import java.io.*;
import java.net.*;
import java.util.*;

public class MasterThread extends Thread{
	
	static ArrayList<Slave> slaves = new ArrayList<Slave>();
	
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket masterSocket = null;
	int id; 
	public MasterThread(ServerSocket ms, int id)
	{
		masterSocket = ms;
		this.id = id;
	}
	
	@Override
	public void run() {
		
		//allow a client to connect to a master thread 
		try (Socket clientSocket = masterSocket.accept();		
				PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
		
			String clientRequest;
			while ((clientRequest = readFromClient.readLine()) != null) {	//receive client request
				System.out.println("Job received from client: " + clientRequest);	
				int slavePort = findAvailable(slaves);		
				System.out.println("Master sent port #" + slavePort);
				writeToClient.println(String.valueOf(slavePort));		//send client the port number for the next available slave
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port 5000 or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
	
	public static int findAvailable(ArrayList<Slave> slaves) {

		Slave current = slaves.get(0); // initialize current to first slave in list

		// loop through to find the slave with the fewest connections
		for (int i = 0; i < slaves.size(); i++) {
			if (slaves.get(i).getCounter() < current.getCounter()) {
				current = slaves.get(i);
			}
		}
		return current.getPort();
	}
}
