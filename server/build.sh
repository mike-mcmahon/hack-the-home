#!/bin/bash
#build.sh
#January 2016
#Mike McMahon, A.Sc.T.
#
#Shell script to build and run DoorServer.java and DoorService.java on 3rdGear server.
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
		
		compile)	javac DoorServer.java;;
		
		run)		java DoorServer 1>>logfile.txt 2>> error.txt;;
		
		*)			echo 'INVALID COMMAND ARGUMENT';;
		
	esac
	
fi