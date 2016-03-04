#!/usr/bin/env bash

export VERSION="1.0-SNAPSHOT"
export E2E_DIR="target/e2e-distrib"
export ZIP=bonita-operation-analyzer-${VERSION}-dist.zip
export UNZIP_DIR=${E2E_DIR}/bonita-operation-analyzer-${VERSION}-dist
export JAR_DIR=${UNZIP_DIR}/lib
export JSON_REPORT=${E2E_DIR}/report.json

mvn clean package -DskipTests

rm -rf ${E2E_DIR}
mkdir ${E2E_DIR}
unzip -d ${E2E_DIR} target/${ZIP}

tree ${UNZIP_DIR}


echo "========================================"
echo "should not contain bonita client/common:"
echo "========================================"
ls -ltr ${JAR_DIR}/bonita*.jar


echo "========================================"
echo "should contain readme:"
echo "========================================"
cat ${UNZIP_DIR}/README.md
