#!/bin/bash
export SPRING_PROFILES_ACTIVE=online
java -jar ./build/libs/test-app-1.0-SNAPSHOT.war --spring.config.location=./config/application.properties
