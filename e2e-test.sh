#!/usr/bin/env bash

export VERSION="1.0-SNAPSHOT"
export E2E_DIR="target/e2e"
export ZIP=bonita-operation-analyzer-${VERSION}-dist.zip
export JAR_DIR=${E2E_DIR}/bonita-operation-analyzer-${VERSION}-dist
export JSON_REPORT=${E2E_DIR}/report.json

# require a running engine on local host with default acme
mvn clean install

rm -rf ${E2E_DIR}
mkdir ${E2E_DIR}
unzip -d ${E2E_DIR} target/${ZIP}

echo "===================================="
echo "should display usage"
echo "===================================="
java -cp ${JAR_DIR} -jar ${JAR_DIR}/bonita-operation-analyzer-${VERSION}.jar

echo "===================================="
echo "should output to console"
echo "===================================="
java -cp ${JAR_DIR} -jar ${JAR_DIR}/bonita-operation-analyzer-${VERSION}.jar -bonitaHome ./bonita-home/ -user install -password install -outputConsole

echo "===================================="
echo "should output to file"
echo "===================================="
java -cp ${JAR_DIR} -jar ${JAR_DIR}/bonita-operation-analyzer-${VERSION}.jar -bonitaHome ./bonita-home/ -user install -password install -outputFile ${E2E_DIR}/report.json

echo "===================================="
echo "${E2E_DIR}/report.json:"
echo "===================================="
cat ${E2E_DIR}/report.json

