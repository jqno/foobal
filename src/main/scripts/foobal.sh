#!/bin/sh
if [ $# -eq 0 ]
then
  java -jar foobal.jar update http://www.fcupdate.nl/programma-uitslagen/s949/nederland-eredivisie-2013-2014/ outcomes.xml
elif [ $# -eq 2 ]
then
  java -jar foobal.jar predict outcomes.xml "$1" "$2"
else
  java -jar foobal.jar "$@"
fi
