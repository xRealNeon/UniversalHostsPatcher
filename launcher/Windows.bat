@echo off
if not "%1"=="am_admin" (powershell start -verb runas '%0' am_admin & exit)
title UniversalHostsPatcher Console
cd %~dp0
java -jar UniversalHostsPatcher