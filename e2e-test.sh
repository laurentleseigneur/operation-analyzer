#!/usr/bin/env bash

export VERSION="1.0-SNAPSHOT"
export E2E_DIR="target/e2e-test"
export ZIP=bonita-operation-analyzer-${VERSION}-dist.zip
export UNZIP_DIR=${E2E_DIR}/bonita-operation-analyzer-${VERSION}-dist
export JAR_DIR=${UNZIP_DIR}/lib
export JSON_REPORT=${E2E_DIR}/report.json
export E2E_JAR=${UNZIP_DIR}/bonita-operation-analyzer-${VERSION}.jar

# requires a running engine on local host with default acme
mvn clean install -e

mkdir -p ${E2E_DIR}
unzip -d ${E2E_DIR} target/${ZIP}

tree target/bonita-lib

tree ${E2E_DIR}

#add excluded provided bonita-client-sp & bonita-common-sp
cp -v target/bonita-lib/*.jar ${JAR_DIR}

echo "===================================="
echo "should display usage"
echo "===================================="
java -jar ${E2E_JAR}

echo "===================================="
echo "should output to console"
echo "===================================="
java -jar ${E2E_JAR} -bonitaHome ./bonita-home/ -user install -password install -outputConsole

echo "===================================="
echo "should output to file"
echo "===================================="
java -jar ${E2E_JAR} -bonitaHome ./bonita-home/ -user install -password install -outputFile ${E2E_DIR}/report.json

echo "===================================="
echo "${E2E_DIR}/report.json:"
echo "===================================="
cat ${E2E_DIR}/report.json

