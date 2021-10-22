#!/bin/bash

while (true)
do
    ROOT=`wget -O - -o /dev/null -q http://avias.home.lan:8080/v1/comms/archive-scan`
    LEN=`echo $ROOT | wc -c`
    if [ $LEN -gt 2 ]
    then
      echo "Scanning $ROOT"
      find "$ROOT" -maxdepth 1 -type d -exec ./filter.sh "$ROOT" "{}" archive-scan \;
      find "$ROOT" -maxdepth 1 -type f -exec ./filter.sh "$ROOT" "{}" archive-file \;
    fi
done