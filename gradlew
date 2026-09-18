#!/usr/bin/env sh
#############################################################################
## Gradle start up script for UN*X
## Auto-generated minimal wrapper script
#############################################################################

if [ -z "$GRADLE_HOME" ]; then
  # prefer wrapper
  DIR="$(cd "$(dirname "$0")" && pwd)"
  if [ -f "$DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
    java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
    exit $?
  fi
fi

exec gradle "$@"
