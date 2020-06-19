@echo off
cd /d %~dp0

set JAVA_HOME="C:\Program Files\Android\Android Studio\jre"
gradlew checkstyle
