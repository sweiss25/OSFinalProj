import java.io.*;
import java.net.*;

public class Client {
    
	public static void main(String[] args) {
		
    	try (Socket clientSocket = new Socket("127.0.0.1", 5000);		//open up communication with Master
    			PrintWriter writeToMaster = new PrintWriter(clientSocket.getOutputStream(), true);
    			BufferedReader readFromMaster = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
    		
    		String job = "This is the job I need done.";
    		writeToMaster.println(job);		//send job request to Master
    		
    		int portNumber = Integer.parseInt(readFromMaster.readLine());	//receive port number from Master
    		System.out.println("Connecting to server with port #" + portNumber);	

    		try (Socket clientSocket2 = new Socket("127.0.0.1", portNumber);		//open up communication with Slave
        			PrintWriter writeToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
    			
    			writeToServer.println(job);		//send job request to Slave
    			String serverResponse = readFromServer.readLine();	//display response from Slave
    			System.out.println(serverResponse);
    			
    			
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
}
