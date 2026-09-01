@echo off
if exist output rd /s /q output

call gradlew.bat buildExtensionDebug || exit /b 1