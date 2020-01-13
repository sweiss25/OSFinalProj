import java.io.*;
import java.net.*;
import java.util.*;

public class Master {

	public static void main(String[] args) {

		final int THREADS = 4;

		try (ServerSocket masterSocket = new ServerSocket(5000);) {
			ArrayList<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < THREADS; i++)
				threads.add(new Thread(new MasterThread(masterSocket, i)));
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
					"Exception caught when trying to listen on port " + "5000"  + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}