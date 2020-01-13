import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
    static int portNumber;
	public static void main(String[] args) {
		
		//client opens up communication with Master
    	try (Socket clientSocket = new Socket("127.0.0.1", 5000);		
    			PrintWriter writeToMaster = new PrintWriter(clientSocket.getOutputStream(), true);
    			BufferedReader readFromMaster = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
    		
    		System.out.println("writing to master");
    		writeToMaster.println("I'm a client");	
    		
    		//client reads in port number of next available slave from master
    		String port = readFromMaster.readLine();	
    		System.out.println("port number from master: " + port);
    		portNumber = Integer.parseInt(port);	
    		
    	} catch (UnknownHostException e) {
            System.err.println("Don't know about host 127.0.0.1");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
            System.exit(1);
        }

    	//client opens up communication with slave
		try (Socket clientSocket2 = new Socket("127.0.0.1", portNumber);		
    			PrintWriter writeToServer = new PrintWriter(clientSocket2.getOutputStream(), true);
    			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));) {
			
			//client sends job to slave
			Scanner input = new Scanner(System.in);
			System.out.println("Enter a statement to be converted to caps: ");
			writeToServer.println(input.nextLine());		
			
			//client displays slave's response
			System.out.println("Your input in caps: " + readFromServer.readLine());	
			
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host 127.0.0.1");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
            System.exit(1);
        }
    	
    }
}
