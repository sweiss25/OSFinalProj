import java.io.*;
import java.net.*;
import java.util.*;

public class Slave {
    private static int counter = 0;	
    private static Random random = new Random();
    private static int portNumber;	

    public Slave() {
    	portNumber = random.nextInt(1000) + 6000;	//generate random port number between 6000-7000
    }
    public static void main(String[] args) {   
    	try (Socket slaveToMasterSocket = new Socket("127.0.0.1", 5000);		//open up communication with Master
    			PrintWriter writeToMaster = new PrintWriter(slaveToMasterSocket.getOutputStream(), true);) {
    		
    		MasterThread.slaves.add(new Slave());	//add slave to the slaves list in master
    		
			try (ServerSocket slaveSocket = new ServerSocket(portNumber);	
					Socket clientSocket = slaveSocket.accept();	
					PrintWriter writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
    	
		
				String clientRequest;
				String slaveResponse;
			
				counter++;
				while ((clientRequest = readFromClient.readLine()) != null) {
					System.out.println("The client job is: " + clientRequest);
				
					slaveResponse = calculateResponse(clientRequest);
				
					System.out.println("Server responding to client: " + slaveResponse);
					writeToClient.println(slaveResponse);
				}
			
			} catch (UnknownHostException e) {
                System.err.println("Don't know about host 127.0.0.1");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
                System.exit(1);
            }
    		
    	} catch (UnknownHostException e) {
            System.err.println("Don't know about host 127.0.0.1");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
            System.exit(1);
        }
    }
	
	public int getCounter() {
		return counter;
	}
	public int getPort() {
		return portNumber;
	}
	
	public static String calculateResponse(String clientRequest) {
		return clientRequest.toUpperCase();
	}
}
