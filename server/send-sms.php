<?php
/*
*	PHP script to issue SMS Text messages to recipients via Twillio.com.
*
* 	---> All standard output to be redirected at commandline to message_log.txt <---
*
*/

require "/usr/local/DOOR_SERVER/vendor/twilio/sdk/Services/Twilio.php";

//Redirect standard output to message_log.txt.
fclose(STDOUT);
$STDOUT = fopen('message_log.txt', 'a+');

//set your AccountSid and AuthToken from www.twilio.com/user/account
$AccountSid = "AC0xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
$AuthToken = "185fxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

//instantiate new Twilio REST client.
$client = new Services_Twilio($AccountSid, $AuthToken);

//People and Numbers to sent SMS too.
$people = array(
	"416xxxxxxx" => "xxxxxxxx",
	"647xxxxxxx" => "Mike",
);

//Send SMS to each person in list.
foreach($people as $number => $name)
{
	$sms = $client->account->messages->sendMessage
	(
		//from
		"647-xxx-xxxx",
		//to
		$number,
		//message body
		"Hey $name! The garage door is open!!"
	);
	
	//Record that message was sent to message_log.txt.
	echo "Sent message $name	";
	echo date('Y-m-d H:i:s');
	echo "\n";
}

?>
