//add master to main of masterThread and rename master

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Master {
	public static void main(String[] args) {
		
		//Hardcode port number if necessary
		args = new String[] {"30122"};
		
		if (args.length != 1)
		{
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		final int THREADS = 5;	
		
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
			ArrayList<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < THREADS; i++)
				threads.add(new Thread(new MasterThread(serverSocket, i)));
			for (Thread t : threads)
				t.start();
			
			
			for (Thread t: threads)
			{
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + "30122"  + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}