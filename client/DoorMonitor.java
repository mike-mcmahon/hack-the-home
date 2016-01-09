/**
 * Raspberry Pi client application that monitors the garage door (via ADT Security panel output contact)
 * and opens communications with 3rdgear server to let the server know if the door has been open
 * for more than 20 minutes and then every 20 minutes for up to three notifications.
 *
 *@author Mike McMahon, A.Sc.T.
 *@date   January 2016
 *
 * Client main class - DoorMonitor
 *
 * ---> All standard output (System.out) to be redirected at commandline to logfile.txt <---
 *
 */

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;
import java.io.*;
import java.net.*;
import java.util.*;


public class DoorMonitor
{
	static private Timer timer;
	static private GpioPinDigitalInput doorContact;
	static private int PORT;
	static private String command;
	static private PrintWriter out;
	static private Socket socket;
	static private OutputStream outStream;
	static int count;
	
	
	public static void main(String[] args) throws InterruptedException
	{	
		//count used to linit number of notifications sent.
		count = 0;
		
		//instantiate object to utilize GPIO.
		GpioController gpio = GpioFactory.getInstance();
		doorContact = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
		doorContact.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            	//if the input contact is closed = HIGH then start the timer
    			//otherwise stop the timer and reset the count to 0.
    			if(event.getState().isHigh())
    			{
					//for testing only
    				System.out.println("Contact closed");
    				//create timer
    				timer = new Timer();
    				int delay = (20 * 60 * 1000);
    				int period = (20 * 60 * 1000);
					//schedule first message to be sent when door is open 20 minutes.
					//then send a message every 20 minutes to a maximum of
					//3 times.
					timer.schedule(new IssueCommand(), delay, period);
    			}
    			else
    			{
    				//for testing only
    				System.out.println("Contact is open");
    				//cancel timer if the garage door is closed.
    				timer.cancel();
					count = 0;
    			}
            }
            
        });	
		
		//keep program running until user aborts (CTRL-C)
		while(true)
		{
			Thread.sleep(1000);
		}
	}
	
	
	static class IssueCommand extends TimerTask
	{
		@Override
		public void run()
		{
			if(count < 3)
			{
				//port number to be used and command to be issued to server.
				PORT = 8888;
				
				//command to be issued to server.
				command = "send_SMS";
			
				//open socket to server and issue command.
				//close socket and streams once complete.
				try
				{
					socket = new Socket("192.168.1.172", PORT);
					outStream =socket.getOutputStream();
					out = new PrintWriter(outStream);
					out.println(command);
					out.flush();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("CAN NOT CONNECT TO SERVER");
				}
				finally
				{	
					//increment number of times a message has been sent.
					count++;
					
					try 
					{
						socket.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}	
				}
			}
		}	
	}
}
