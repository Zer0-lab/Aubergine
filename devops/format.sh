#!/usr/bin/env bash

files=${1:-$(find src -name "*.java")} && \
java -jar /usr/local/lib/google-java-format.jar --replace $files;