@echo off
setlocal

set MYSQL_DATABASE=dept_db
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASSWORD=root
set MAX_RETRIES=30

echo =====================================
echo Checking if MySQL is ready...
echo =====================================

set RETRIES=0
:wait_for_mysql
docker run --rm ^
  --network="host" ^
  mysql:8.3 ^
  mysqladmin ping -h127.0.0.1 -P%MYSQL_PORT% -u%MYSQL_USER% -p%MYSQL_PASSWORD% --silent

IF %ERRORLEVEL% EQU 0 (
    echo ✅ MySQL is up!
    goto run_liquibase
)

set /a RETRIES+=1
IF %RETRIES% GEQ %MAX_RETRIES% (
    echo ❌ MySQL did not start after %MAX_RETRIES% attempts. Exiting.
    exit /b 1
)

echo 🔄 Waiting for MySQL... (Attempt %RETRIES%/%MAX_RETRIES%)
timeout /t 2 /nobreak >nul
goto wait_for_mysql

:run_liquibase

echo =====================================
echo Running Liquibase migrations...
echo =====================================

docker run --rm ^
  -v "%cd%\db\mysql-connector-java-9.3.0.jar:/liquibase/mysql-connector-java-9.3.0.jar" ^
  -v "%cd%\db\changelog:/liquibase/changelog" ^
  liquibase/liquibase ^
  --url=jdbc:mysql://host.docker.internal:%MYSQL_PORT%/%MYSQL_DATABASE% ^
  --driver=com.mysql.cj.jdbc.Driver ^
  --classpath=/liquibase/mysql-connector-java-9.3.0.jar ^
  --searchPath=/liquibase/changelog ^
  --changeLogFile=changelog.xml ^
  --username=%MYSQL_USER% ^
  --password=%MYSQL_PASSWORD% ^
  update

IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Liquibase migration failed!
    exit /b %ERRORLEVEL%
)

echo ✅ Liquibase migration completed!

pause