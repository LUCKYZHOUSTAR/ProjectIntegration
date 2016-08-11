#!/bin/sh

cd `dirname $0`
cd ..
mvn dependency:sources -DdownloadSources=false -DdownloadJavadocs=false
cd -
