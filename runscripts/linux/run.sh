#!/bin/bash

################### ATTENTION !! ####################
# Use this file to run imgurDiscoverer and do not	#
# run it by double clicking the *.jar file!			#
# This would result in big trouble with the system	#
# resources because no JVM args are provided then.	#
#####################################################

min=-Xms128M
max=-Xmx325m
gttimeRatio=-XX:GCTimeRatio=19
noExpGc=-XX:+DisableExplicitGC
parallelOldGc=-XX:+UseParallelOldGC
maxGcpause=-XX:MaxGCPauseMillis=150
heapOccAuto=-XX:InitiatingHeapOccupancyPercent=0

echo "Starting run script."
echo "Checking for Java..."
if type -p java; then
	echo "Found Java. Starting imgurDiscoverer!"
	java $min $max $ggttimeRatio $noExpGc $parallelOldGc $maxGcpause $heapOccAuto -jar imgurDiscoverer.jar valid
else
	echo "Can't find any Java. Can't start imgurDiscoverer"
	echo "Download Java at: https://java.com/de/download/"
	echo "Or might use ppa: http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html"
fi