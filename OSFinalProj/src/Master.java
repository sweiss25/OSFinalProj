import java.io.*;
import java.net.*;
import java.util.*;

public class Master extends Thread {

	public static void main(String[] args) {

		final int THREADS = 5;

		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < THREADS; i++)
			threads.add(new Thread(new Master(serverSocket, i)));
		for (Thread t : threads)
			t.start();

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		static ArrayList<Slave> slaves = new ArrayList<Slave>();
	}

	@Override
	public void run() {

		//allow a client to connect to the master's server socket
		try (ServerSocket masterSocket = new ServerSocket(5000);	
				Socket clientSocket = masterSocket.accept();	
				PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
		
			String clientRequest;
			while ((clientRequest = readFromClient.readLine()) != null) {	//receive client request
				System.out.println("Job received from client #" + clientId + ": " + clientRequest);	
				int slavePort = findAvailable(slaves);		
				System.out.println("Client sent port #" + slavePort);
				writeToClient.println(slavePort);		//send client the port number for the next available slave
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port 5000 or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	public static int findAvailable(ArrayList<Slave> slaves) {

		Slave curr = slaves.get(0); // initialize curr to first slave in list

		// loop through to find the slave with the fewest connections
		for (int i = 0; i < slaves.size(); i++) {
			if (slaves.get(i).getCounter() < curr.getCounter()) {
				curr = slaves.get(i);
			}
		}
		return curr.getPort();
	}
}
