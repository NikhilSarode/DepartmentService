FROM liquibase/liquibase:latest

# Copy the MySQL JDBC driver into the Docker image
COPY mysql-connector-java-9.3.0.jar /liquibase/lib/
COPY changelog /liquibase/changelog/