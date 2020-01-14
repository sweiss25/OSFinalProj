import java.io.*;
import java.net.*;
import java.util.*;

public class Slave {
	
    public static void main(String[] args) { 
    	
    	//generate random port number between 6000-7000
    	Random random = new Random();
		int portNum = random.nextInt(1000) + 6000;	

		//slave opens up communication with Master
    	try (Socket slaveToMasterSocket = new Socket("127.0.0.1", 5000);		
    			PrintWriter writeToMaster = new PrintWriter(slaveToMasterSocket.getOutputStream(), true);
    			BufferedReader readFromMaster = new BufferedReader(new InputStreamReader(slaveToMasterSocket.getInputStream()));) {
    		
    		//slave sends its port number to master
    		System.out.println("writing to master");
    		writeToMaster.println(portNum);		
    		
    		//client opens up communication with slave
    		try (ServerSocket slaveToClientSocket = new ServerSocket(portNum);		
    				Socket clientSocket = slaveToClientSocket.accept();	
    				PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
    				BufferedReader readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
    		
    			String clientRequest;
    			
    			//slave reads client's statement
    			while ((clientRequest = readFromClient.readLine()) != null) {
    				System.out.println("The client sent: " + clientRequest);	
    			
    				//slave sends back client's statement in capital letters
    				System.out.println("slave responding to client");
    				writeToClient.println(clientRequest.toUpperCase());		
    			}
    		
    		} catch (IOException e) {
    			System.out.println(
    					"Exception caught when trying to listen on port " + portNum + " or listening for a connection");
    			System.out.println(e.getMessage());
    		}
    		
    	} catch (UnknownHostException e) {
            System.err.println("Don't know about host 127.0.0.1");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
            System.exit(1);
        }
		
    	
    	
    }
    
}
