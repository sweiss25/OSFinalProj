
import java.io.*;
import java.net.*;

public class Slave {
    private static int counter;
    private static int portNumber;

	public static void main(String[] args) throws IOException {
    	        
		// Hardcode in IP and Port here if required
    	args = new String[] {"127.0.0.1", "30121"};
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        portNumber = Integer.parseInt(args[1]);

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
        	
        	
            String userInput;
			String serverResponse;
            while ((userInput = stdIn.readLine()) != null) {
                requestWriter.println(userInput); // send request to server
				serverResponse = responseReader.readLine();
                System.out.println("SERVER RESPONDS: \"" + serverResponse + "\"");
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
	
	public int getCounter() {
		return counter;
	}
	public int getPort() {
		return portNumber;
	}
}
