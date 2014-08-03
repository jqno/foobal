#!/bin/sh
if [ $# -eq 0 ]
then
  java -jar foobal.jar update "http://www.eredivisiestats.nl/wedstrijden.php?submit=OK&seizoen[]=2014-2015" outcomes.xml
elif [ $# -eq 2 ]
then
  java -jar foobal.jar predict outcomes.xml "$1" "$2"
else
  java -jar foobal.jar "$@"
fi
