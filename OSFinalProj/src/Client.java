//rearrange port situation, client has to connect to slave port

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        
		// Hardcode in IP and Port here if required
    	args = new String[] {"127.0.0.1", "30122"};
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter requestWriter = // stream to write text requests to server
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader responseReader= // stream to read text response from server
                new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())); 
            BufferedReader stdIn = // standard input stream to get user's requests
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
        	
        	
            String userInput = "request slave";
			String serverResponse = "";
            while ((userInput = stdIn.readLine()) != null) {
            	System.out.println("CLIENT REQUESTS A SLAVE");
                requestWriter.println(userInput); // send request to server
				serverResponse = responseReader.readLine();
				//server responds with the slave's port
                System.out.println("SERVER RESPONDS: \"" + serverResponse + "\"");
            }
            
            try (
                    Socket clientSocket2 = new Socket(hostName, Integer.parseInt(serverResponse));
                    PrintWriter requestWriterToSlave = // stream to write text requests to server
                        new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader responseReaderFromSlave = // stream to read text response from server
                        new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream())); 
                    BufferedReader stdInToSlave = // standard input stream to get user's requests
                        new BufferedReader(
                            new InputStreamReader(System.in))
                ){
            	String userInputToSlave;
            	String slaveResponse;
            	while((userInputToSlave = stdInToSlave.readLine()) != null) {
            		System.out.println("CLIENT SENDS REQUEST TO SLAVE");
            		requestWriter.println(userInputToSlave);
            		slaveResponse = responseReaderFromSlave.readLine();
            		System.out.println("SLAVE RESPONDS: \"" + slaveResponse + "\"");
            	}
            	
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}
