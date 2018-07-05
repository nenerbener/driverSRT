#!/bin/sh

#Script to read from $1 (multiple line separated Youtube URLs)a and run
#through all URLs downloading closed caption data into named files located
#in the output directory. 
for i in $(cat $1); do
			java -cp "build/classes:build/lib/jdom-1.0.jar:build/lib/commons-io-2.4.jar:build/resources" com.nenerbener.driverSRT.DriverSRT "./output"  $i
		done
