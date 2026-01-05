@echo off
if exist output rd /s /q output

gradlew.bat assembleDebug || exit /b 1
gradlew.bat :app:createFinalZip || exit /b 1