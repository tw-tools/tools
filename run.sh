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
  ./mvnw -Pdefault clean release:clean package release:prepare release:perform
}

run_default
#run_renamefilesanddirs
#run_scaleimages
#run_doc
#release



