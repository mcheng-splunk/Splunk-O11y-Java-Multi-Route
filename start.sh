#! /bin/bash

java -javaagent:./splunk-otel-javaagent.jar \
-Djdk.internal.httpclient.disableHostnameVerification=true \
-Dotel.service.name="mcheng-cc-java-multi-route" \
-Dotel.resource.attributes='deployment.environment=mcheng-java-env' \
-jar ./target/multi-route-0.0.1-SNAPSHOT.jar
