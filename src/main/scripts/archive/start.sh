#!/bin/bash

ROOT=/c
for DIR in `find $ROOT -maxdepth 1 -type d`
do
    ./filter.sh "$ROOT" "$DIR" archive-scan
done
