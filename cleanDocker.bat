@echo off

echo stopping containers...
for /f "tokens=*" %%i in ('docker ps -aq') do docker stop %%i

echo removing containers...
for /f "tokens=*" %%i in ('docker ps -aq') do docker rm %%i

echo removing images...
for /f "tokens=*" %%i in ('docker images -q') do docker rmi %%i

echo removing volumes...
for /f "tokens=*" %%i in ('docker volume ls -q') do docker volume rm %%i

echo done!