#!/usr/bin/env bash

function run_default () {
  ./mvnw -Pdefault clean package exec:java
}

function run_renamefilesanddirs () {
  ./mvnw -Pdrenamefilesanddirs clean package exec:java
}

function run_scaleimages () {
  ./mvnw -Pscaleimages clean package exec:java
}

function run_doc () {
  ./mvnw -Pdoc clean package exec:java
}

function release () {
  ./mvnw --batch-mode -Pdefault -Dtag=tools-0.0.1 release:prepare -DreleaseVersion=0.0.1 -DdevelopmentVersion=0.0.2-SNAPSHOT

  ./mvnw -Pdefault clean release:clean package release:prepare release:perform
}

run_default
#run_renamefilesanddirs
#run_scaleimages
#run_doc
#release



