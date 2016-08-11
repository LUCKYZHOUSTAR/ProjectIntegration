#!/bin/bash

cd `dirname $0`
cd ..
mvn clean eclipse:clean idea:clean
cd -
