#!/bin/sh
if [ $# -eq 0 ]
then
  java -jar foobal.jar update "https://www.fcupdate.nl/uitslagen/s1407/nederland-keuken-kampioen-divisie-2019-2020/" outcomes.xml
elif [ $# -eq 2 ]
then
  java -jar foobal.jar predict outcomes.xml "$1" "$2"
else
  java -jar foobal.jar "$@"
fi
