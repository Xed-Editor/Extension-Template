@echo off
if exist output rd /s /q output

gradlew.bat assembleRelease || exit /b 1
gradlew.bat :app:createFinalZip || exit /b 1