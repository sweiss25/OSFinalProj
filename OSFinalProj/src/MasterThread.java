import java.io.*;
import java.net.*;
import java.util.*;

public class MasterThread extends Thread{
	
	public static final ArrayList<String> portNumbers = new ArrayList<>();
	
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
		
		//slave or client opens up communication with Master
		try (Socket slaveSocket = masterSocket.accept();		
				PrintWriter writeToClient = new PrintWriter(slaveSocket.getOutputStream(), true);
				BufferedReader readFromClient = new BufferedReader(new InputStreamReader(slaveSocket.getInputStream()));) {
			
			String read;	//variable to hold value read in from slave/client
			
			//assign type based on whether a client or slave has written in
			String type; 	
			if((read = readFromClient.readLine()).equals("I'm a client")) {
				type = "client";
			} else {
				type = "slave";
			}
			
			//if type is slave, master reads in slaves port number and adds it to the list of slaves
			String portNumber;
			if (read != null && type.equals("slave")) {
				portNumber = read;
				System.out.println(portNumber + " received by listener: " + id);	//receive port number from slave
				portNumbers.add(portNumber);
			}
			
			//if type is client, master finds the next available slave and sends its port number to the client
			String job;
			if (read != null && type.equals("client")) {
				job = read;
				System.out.println("client says: " + job);
				
				String availablePort = findAvailable(portNumbers);		//find slave with fewest current connections
				
				writeToClient.println(availablePort);
				System.out.println("port sent to client: " + availablePort);

			}
			
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port 5000 or listening for a connection");
			System.out.println(e.getMessage());
		}
		
		
	}
	
	//method to find the slave with the fewest current connections
	public static String findAvailable(ArrayList<String> portNumbers) {
		/*
		String current = portNumbers.get(0);
		for (int i = 0; i < portNumbers.size(); i++) {
			if (portNumbers < current) {
				current = slaves.get(i);
			}
		}
		*/
		return portNumbers.get(0);
	}
}
