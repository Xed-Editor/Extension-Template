@echo off
if exist output rd /s /q output

call gradlew.bat assembleDebug || exit /b 1
call gradlew.bat :app:createFinalZip || exit /b 1