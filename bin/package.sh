#!/bin/bash

cd `dirname $0`
cd ..
p="local"
if [ ! -n "$1" ] ;then
    p="local"
else
    p="$1"
fi
mvn clean package -Dmaven.skip.test=true -P $p
cd -
