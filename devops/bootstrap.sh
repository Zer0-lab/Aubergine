#!/usr/bin/env bash

lefthook install && \
gitmoji --init && \
gitmoji --update && \
./devops/install.sh
