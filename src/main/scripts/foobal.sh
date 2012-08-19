#!/bin/sh
if [ "$1" = "update" ]
then
  java -jar foobal.jar update http://www.fcupdate.nl/programma-uitslagen/s819/nederland-eredivisie-2012-2013/ $2 $3
else
  java -jar foobal.jar "$@"
fi
