@echo off
if exist output rd /s /q output

call gradlew.bat buildExtensionRelease || exit /b 1