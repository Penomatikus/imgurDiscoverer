@echo off
ECHO Starting run script.
ECHO Checking for Java...

SET min=-Xms128M
SET max=-Xmx325m
SET gttimeRatio=-XX:GCTimeRatio=19
SET noExpGc=-XX:+DisableExplicitGC
SET parallelOldGc=-XX:+UseParallelOldGC
SET maxGcpause=-XX:MaxGCPauseMillis=150
SET heapOccAuto=-XX:InitiatingHeapOccupancyPercent=0

FOR /f "delims=" %%F IN ('where java') DO SET var=%%F 

IF EXIST %var% (
	echo --
	echo Found Java. Starting imgurDiscoverer!
	java %min% %max% %ggttimeRatio% %noExpGc% %parallelOldGc% %$maxGcpause% %heapOccAuto% -jar imgurDiscoverer.jar valid
) ELSE (
	echo --
	echo Can't find any Java. Can't start imgurDiscoverer.
	echo Download Java at: https://java.com/de/download/
	echo --
	pause
)


