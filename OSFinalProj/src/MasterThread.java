
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MasterThread implements Runnable {
	
	static ArrayList<Slave> slaves = new ArrayList<Slave>();
	static Random globalRnd = new Random();
	Random localRnd = new Random(globalRnd.nextLong());
	//MagicEightBall magicEightBall = new MagicEightBall(localRnd);
	//Slave slave1 = new Slave();
	//Slave slave2 = new Slave();
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverSocket = null;
	int id; 
	public MasterThread(ServerSocket s, int id)
	{
		serverSocket = s;
		this.id = id;
	}
	
	@Override
	public void run() {

		// This thread accepts its own client socket from the shared server socket
		try (Socket clientSocket = serverSocket.accept();
				PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			
			String requestString;
			while ((requestString = requestReader.readLine()) != null) {
				System.out.println(requestString + " received by listener: " + id);
				if(requestString == "request slave") {
					System.out.println("RESPONDING TO CLIENT" + findAvailable(slaves));
					responseWriter.println(findAvailable(slaves));
				}
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + serverSocket.getLocalPort() + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
	
	public static int findAvailable(ArrayList<Slave> slaves) {
		
		Slave curr = slaves.get(0);	//initialize curr to first slave in list
		
		//loop through to find the slave with the fewest connections
		for(int i = 0; i < slaves.size(); i++) {
			if(slaves.get(i).getCounter() < curr.getCounter()) {
				curr = slaves.get(i);
			}
		}
		return curr.getPort();
	}
}
