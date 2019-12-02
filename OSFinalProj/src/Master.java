import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Master {
	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(5000);	
			Socket clientSocket = serverSocket.accept();	
			PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			
			ArrayList<Slave> slaves = new ArrayList<>();	//list to hold the slaves
			Slave slave1 = new Slave();
			Slave slave2 = new Slave();
			Slave slave3 = new Slave();
		
			//add slaves to the list 
			slaves.add(slave1);	
			slaves.add(slave2);
			slaves.add(slave3);
			
			//call method to determine which slave has the fewest current connections
			Slave available = findAvailable(slaves);
			
			//Send the client the port number of the available slave
			responseWriter.println(available.getPort());

		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port 5000 or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
	
	//Determine which slave has the fewest current connections 
	public static Slave findAvailable(ArrayList<Slave> slaves) {
		
		Slave curr = slaves.get(0);	//initialize curr to first slave in list
		
		//loop through to find the slave with the fewest connections
		for(int i = 0; i < slaves.size(); i++) {
			if(slaves.get(i).getCounter() < curr.getCounter()) {
				curr = slaves.get(i);
			}
		}
		return curr;
	}
}