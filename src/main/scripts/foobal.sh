#!/bin/sh
if [ $# -eq 0 ]
then
  java -jar foobal.jar update "http://www.fcupdate.nl/programma/s1146/nederland-jupiler-league-2015-2016/" outcomes.xml
elif [ $# -eq 2 ]
then
  java -jar foobal.jar predict outcomes.xml "$1" "$2"
else
  java -jar foobal.jar "$@"
fi
