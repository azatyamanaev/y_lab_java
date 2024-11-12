#!/bin/bash

# set JAVA_OPTIONS to pass additional JVM options
# example: JAVA_OPTIONS="-Xms3G -Xmx3G"

# shellcheck disable=SC2086
exec java $JAVA_OPTIONS -jar app.jar "$@"
