# hack-the-home
A neat home automation fix to a nagging problem. 

Inspired by the hacker-scripts repo, I created a solution taking advantage of the Twilio.com REST API.  I used a Raspberry Pi
and iterfaced it with my existing ADT Security system, did a little networking, connected to my server, and voila!

To elaborate a little further; I configured a relay output on my ADT control board to change state whenever the garage door was open.  
Using an interposing relay, I toggled a GPIO input on a Raspberry Pi which I mounted in an enclosure next to the ADT security 
panel, which is hardwired into the LAN of my home.  The client software 'DoorMonitor' monitors the state of the GPIO input 
and when closed for more than 20 minutes, opens a socket to my server and sends a message.  This message is sent to the server 
every 20 minutes to a maximum of three times (if the door remains open).  The server software, 'DoorServer' and 'DoorService', then
executes code which then calls and executes a PHP script.  The PHP script 'send-sms' then makes use of Twilio's REST API to send a SMS 
text message to me.

To keep things simple I used a simple shell script for the builds, and used UpStart to make the application run as a service/daemon on 
both the Pi and server.

Cheers!

Mike
