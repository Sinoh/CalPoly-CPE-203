#!/bin/sh

javac *.java
if [ $? != 0 ] ; then
    exit 1
fi
java TestWineInventory
rm *.class
