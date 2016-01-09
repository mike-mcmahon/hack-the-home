#!/bin/bash
#build.sh
#January 2016
#Mike McMahon, A.Sc.T.
#
#Shell script to build and run DoorMonitor.java on Raspberry Pi.
#
#Command arguments:
#	-clean -> removes old .class files
#	-compile -> compiles .java file to .class files
#	-run -> runs the java program

argument=$1

if [ $# -gt 1 ]
	
then
	
	echo 'TOO MANY COMMAND LINE ARGUMENTS'
	
else
	
	case ${argument} in
		
		clean)		rm *.class;;
		
		compile)	javac -classpath .:classes:/opt/pi4j/lib/'*' -d . DoorMonitor.java;;
		
		run)		sudo java -classpath .:classes:/opt/pi4j/lib/'*' DoorMonitor 1>>logfile.txt 2>> error.txt;;
		
		*)			echo 'INVALID COMMAND ARGUMENT';;
		
	esac
	
fi
