#!/bin/sh
# Maven Wrapper
exec "$JAVA_HOME/bin/java" -classpath ".mvn/wrapper/maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain "$@" 2>/dev/null || \
  exec mvn "$@"
