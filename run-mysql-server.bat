@echo off
setlocal

set MYSQL_CONTAINER_NAME=mysql-container
set MYSQL_ROOT_PASSWORD=root
set MYSQL_DATABASE=dept_db
set MYSQL_PORT=3306

echo =====================================
echo Starting MySQL container...
echo =====================================

:: Stop and remove any existing container
docker stop %MYSQL_CONTAINER_NAME% >nul 2>&1
docker rm %MYSQL_CONTAINER_NAME% >nul 2>&1

:: Start new MySQL container
docker run -d ^
 --name %MYSQL_CONTAINER_NAME% ^
 -e MYSQL_ROOT_PASSWORD=%MYSQL_ROOT_PASSWORD% ^
 -e MYSQL_DATABASE=%MYSQL_DATABASE% ^
 -p %MYSQL_PORT%:3306 ^
 mysql:8.3

IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Failed to start MySQL container!
    exit /b %ERRORLEVEL%
)

echo ✅ MySQL container started!

pause
