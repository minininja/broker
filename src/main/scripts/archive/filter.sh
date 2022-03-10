#!/bin/bash

#
# arguments
#   1 - the exemplar, what not to send
#   2 - the current value
#   3 - where to send it
#

if [ "$1" != "$2" ]
then
  echo "Sending $2"
  wget --post-data="$2" "http://mini:30010/v1/comms/$3" --header="Content-Type: text/plain" -q -O /dev/null
  while [ $? -ne 0 ]
  do
    sleep 0.5
    wget --post-data="$2" "http://mini:30010/v1/comms/$3" --header="Content-Type: text/plain" -q -O /dev/null
  done
fi