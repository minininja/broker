#!/bin/sh
while (true)
do
    FILE=`wget -O - -o /dev/null -q http://mini:30010/v1/comms/archive-file`
    CNT=`echo $FILE | wc -c`
    if [ $CNT -gt 2 ];
    then
      echo $FILE
      SUM=`md5sum "$FILE"`
      wget --post-data="$SUM" http://mini:30010/v1/comms/archive-file-md5 --header="Content-Type: text/plain" -q -O /dev/null
      while [ $? -ne 0 ]
      do
        sleep 0.5
        wget --post-data="$SUM" http://mini:30010/v1/comms/archive-file-md5 --header="Content-Type: text/plain" -q -O /dev/null
      done
    fi
done
