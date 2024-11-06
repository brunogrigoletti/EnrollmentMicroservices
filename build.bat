@echo off

REM Diretório inicial
set BASE_DIR=%cd%

REM mvn install em commons
cd /d "%BASE_DIR%\commons"
call mvn install

REM Diretório inicial
cd /d "%BASE_DIR%"

REM mvn package em students
cd /d "%BASE_DIR%\students"
call mvn package

REM Diretório inicial
cd /d "%BASE_DIR%"

REM mvn package em subjects
cd /d "%BASE_DIR%\subjects"
call mvn package