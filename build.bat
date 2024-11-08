@echo off

set BASE_DIR=%cd%

REM mvn install em commons
echo installing commons...
cd /d "%BASE_DIR%\commons"
call mvn install

cd /d "%BASE_DIR%"

REM mvn package em students
echo packaging students...
cd /d "%BASE_DIR%\students"
call mvn package

cd /d "%BASE_DIR%"

REM mvn package em subjects
echo packaging subjects...
cd /d "%BASE_DIR%\subjects"
call mvn package