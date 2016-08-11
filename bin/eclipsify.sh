#!/bin/bash

cd `dirname $0`
cd ..
mvn clean eclipse:eclipse -Dwtpversion=1.0
cd -
