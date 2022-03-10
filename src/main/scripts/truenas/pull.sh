#!/bin/sh

PID=$$

USER=$1
PASSWD=$2

while (true)
do
    MSG=`wget -O - -o /dev/null -q http://mini:30010/v1/comms/truenas-file-pull`
    if [ $? -eq 0 ]
    then
      echo $MSG
      SRC=`echo $MSG | cut -f1 -d\| | cut -f3- -d/ | sed -e 's/  /$%20%20/g' | sed -e 's/ /%20/g'`
      DST=`echo $MSG | cut -f2 -d\|`

      echo $SRC $DST

      # for the source file we'll pull it via http
      echo "pulling \"$SRC\""
      echo wget --no-check-certificate --user=$USER --password=$PASSWD "https://archive.home.lan/share/${SRC}" -O tmp_$PID
      wget --no-check-certificate --user=$USER --password=$PASSWD "https://archive.home.lan/share/${SRC}" -O tmp_$PID -o /dev/null
      R=$?
      if [ $R -eq 0 ]
      then
        echo "moving \"$SRC\" to \"$DST\""
        cp tmp_$PID "$DST"
      else
        echo "failed with $R - $SRC"
      fi
      break
    fi
done
