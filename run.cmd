rem @echo "set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"



rem mvnw -Pdefault clean install exec:java

rem mvnw -Pdrenamefilesanddirs clean install exec:java

rem mvnw -Pscaleimages clean install exec:java

rem mvnw -Pdoc clean install exec:java

rem mvnw -Pdefault clean release:clean package release:prepare release:perform

mvnw --batch-mode -Pdefault -Dtag=tools-0.0.1 release:prepare -DreleaseVersion=0.0.1 -DdevelopmentVersion=0.0.2-SNAPSHOT
