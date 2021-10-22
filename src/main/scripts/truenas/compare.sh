#!/bin/sh
while (true)
do
    MSG=`wget -O - -o /dev/null -q http://avias.home.lan:8080/v1/comms/archive-file-md5`
    CNT=`echo $MSG | wc -c`
    if [ $CNT -gt 2 ];
    then
      # messages look like
      #
      #  43124312432 /c/filename
      #
      # the first part is the md5 sum, the second the filename
      # files on archive start with a /c
      # we want to drop that and add /mnt/main/readynas instead

      REMOTE_SUM=`echo $MSG | cut -d" " -f1`
      REMOTE_NAME=`echo $MSG | cut -d" " -f2-`
      STRIPPED_NAME=`echo $MSG | cut -f3- -d/`
      LOCAL_NAME="/mnt/main/readynas/$STRIPPED_NAME"
      LOCAL_SUM=`md5 -r "$LOCAL_NAME" | cut -f1 -d" "`

      if [ "$REMOTE_SUM" != "$LOCAL_SUM" ];
      then
        echo "mismatch $REMOTE_NAME $REMOTE_SUM $LOCAL_SUM $LOCAL_NAME"
        wget --post-data="${REMOTE_NAME}|${LOCAL_NAME}" http://avias.home.lan:8080/v1/comms/truenas-file-pull --header="Content-Type: text/plain" -q -O /dev/null
        while [ $? -ne 0 ]
        do
          sleep 0.5
          wget --post-data="${REMOTE_NAME}|${LOCAL_NAME}" http://avias.home.lan:8080/v1/comms/truenas-file-pull --header="Content-Type: text/plain" -q -O /dev/null
        done
      else
        echo "match $REMOTE_NAME"
      fi
    fi
done
