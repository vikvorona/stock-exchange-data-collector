#!/bin/bash

region=$1

dir=C:/Users/Yana/IdeaProjects/stock-exchange-data-collector/sedc-core/target/
jar_name=sedc-core-0.1-SNAPSHOT.jar

if [ -z "$1" ]; then
        echo "Missing arguments, exiting.."
        echo "Usage : $0 region"
        exit 1
fi

java -cp $dir/$jar_name com.sedc.collectors.yahoo.xchange.YahooXchangeApplication $region