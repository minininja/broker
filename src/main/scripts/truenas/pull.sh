#!/bin/sh
while (true)
do
    MSG=`wget -O - -o /dev/null -q http://avias.home.lan:8080/v1/comms/truenas-file-pull`
    if [ $? -eq 0 ]
    then
      SRC=`echo $MSG | cut -f1 -d\| sed -e "s/ /\\ /g"`
      DST=`echo $MSG | cut -f2 -d\|`
      echo "pulling \"$SRC\" to \"$DST\""
      scp root@archive.home.lan:"$SRC" "$DST"
    fi
done
