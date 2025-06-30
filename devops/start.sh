#!/usr/bin/env bash

profiles="-Dspring-boot.run.profiles"
case "$1" in
  --queue)
    profiles="$profiles=queue"
    ;;
  --scheduler)
    profiles="$profiles=scheduler"
    ;;
  *)
    profiles="$profiles=http"
    ;;
esac

mvn spring-boot:run $profiles
