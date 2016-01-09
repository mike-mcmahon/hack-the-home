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
 *Server class - DoorService
 *
 * ---> All standard output (System.out) to be redirected at commandline to logfile.txt <---
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;


public class DoorService implements Runnable
{
	private Socket socket;
	private Scanner in;
	
	//construct DoorService object.
	public DoorService(Socket s)
	{
		socket = s;
	}
	
	@Override
	public void run()
	{
		try
		{
			try
			{
				//create input Scanner and call doService().
				in = new Scanner(socket.getInputStream());
				doService();
			}
			finally
			{
				socket.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void doService() throws IOException
	{
		while(true)
		{
			if(!in.hasNext())
			{
				return;
			}
			//if command "send_SMS" is received execute the command 
			//method to issue SMS message.
			String command = in.nextLine();
			executeCommand(command);
		}
	}
	
	
	public void executeCommand(String command)
	{
		if(command.equals("send_SMS"))
		{
			try
			{
				//execute PHP script that calls Twilio.com to sent SMS text message.
				Process process = Runtime.getRuntime().exec("php send-sms.php\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}