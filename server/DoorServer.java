/**
 * 3rdGear server application that monitors port 8888 for incoming connections from Raspberry Pi on 
 * IP address 192.168.1.47.
 * 
 * When connection is established, looks for command "Send_SMS" and then this program
 * will execute a script to make a HTTP request to send a SMS message to my cell phone.
 *
 *@author Mike McMahon, A.Sc.T.
 *@date   January 2016
 *
 *Server main class - DoorServer
 *
 * ---> All standard output (System.out) to be redirected at commandline to logfile.txt <---
 *
 */

import java.io.*;
import java.net.*;


public class DoorServer 
{
	public static void main(String[] args) throws IOException
	{
		//using port 8888 and creating a ServerSocket.
		final int PORT = 8888;
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("Waiting for client to connect...");
		
		while(true)
		{
			//monitor ServerSocket for incoming connections from client.
			//when a connection is made, execute service.
			Socket socket = server.accept();
			System.out.println("Client­connected.");
			DoorService service = new DoorService(socket);
			Thread thread = new Thread(service);
			thread.start();
		}
	}
}
