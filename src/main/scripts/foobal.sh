#!/bin/sh
if [ $# -eq 0 ]
then
  java -jar foobal.jar update "https://www.fcupdate.nl/uitslagen/s1330/nederland-eredivisie-2018-2019/" outcomes.xml
elif [ $# -eq 2 ]
then
  java -jar foobal.jar predict outcomes.xml "$1" "$2"
else
  java -jar foobal.jar "$@"
fi
