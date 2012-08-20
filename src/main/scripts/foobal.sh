#!/bin/sh
if [ $# -eq 0 ]
then
  java -jar foobal.jar update http://www.fcupdate.nl/programma-uitslagen/s819/nederland-eredivisie-2012-2013/ outcomes.xml
elif [ $# -eq 2 ]
then
  java -jar foobal.jar predict outcomes.xml "$1" "$2"
else
  java -jar foobal.jar "$@"
fi
