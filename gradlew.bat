@echo off
REM minimal gradle wrapper stub
set DIR=%~dp0
if exist "%DIR%gradle\wrapper\gradle-wrapper.jar" (
  java -jar "%DIR%gradle\wrapper\gradle-wrapper.jar" %*
  exit /b %ERRORLEVEL%
)
gradle %*
