//each time a client connects, increment counter

import java.io.*;
import java.net.*;
import java.util.*;

public class Slave extends Thread {
	private static Random random = new Random();
    private static int counter;
    private static int portNumber = random.nextInt();

    @Override
	public void run() {        
    	try (ServerSocket slaveSocket = new ServerSocket(portNumber);	
    			Socket clientSocket = slaveSocket.accept();	
				PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
        	
        	
            String clientRequest;
			String slaveResponse;
			
			while ((clientRequest = readFromClient.readLine()) != null) {
				System.out.println("The client job is: " + clientRequest);
			
				slaveResponse = calculateResponse();
			
				System.out.println("Server responding to client: " + slaveResponse);
				writeToClient.println(slaveResponse);
			}
			
    	} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
    	
    }
	
	public int getCounter() {
		return counter;
	}
	public int getPort() {
		return portNumber;
	}
}
