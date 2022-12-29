#!/bin/bash

if [ $1 ];
    then
    mvn clean package
fi

$DEV_JDK/bin/java -XX:ValueTypeFlatteningThreshold=99999 -Xjit:debugCounters={InlineStatistics*},staticDebugCounters={InlineStatistics*} -cp target/valuetypes-comparison-1.1.jar:lib/gson-2.8.5.jar in.ac.iitmandi.compl.ValueMovieQueries ./dataset/movies.json | tee value-types.log

$DEV_JDK/bin/java -XX:ValueTypeFlatteningThreshold=99999 -Xjit:debugCounters={InlineStatistics*},staticDebugCounters={InlineStatistics*} -cp target/valuetypes-comparison-1.1.jar:lib/gson-2.8.5.jar in.ac.iitmandi.compl.UnboxedMovieQueries ./dataset/movies.json | tee non-value-types.log 
