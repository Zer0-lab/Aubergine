#!/usr/bin/env bash

debug=""
if [ "$2" == "--debug" ]; then
  debug="-Dmaven.surefire.debug"
fi

mvn test -Dtest="$1" $debug
