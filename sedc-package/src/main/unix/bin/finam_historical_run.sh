#!/bin/bash

period=$1
region=$2

dir=C:/Users/Yana/IdeaProjects/stock-exchange-data-collector/sedc-core/target
jar_name=sedc-core-0.1-SNAPSHOT.jar

if [ -z "$1" ] || [ -z "$2" ]; then
        echo "Missing arguments, exiting.."
        echo "Usage : $0 period region"
        exit 1
fi

java -cp $dir/$jar_name com.sedc.collectors.finam.historical.FinamHistoricalApplication $period $region