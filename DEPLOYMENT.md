# Deployment Guide

This guide provides step-by-step instructions for deploying the Inventory Control System.

## Quick Start with Docker (Recommended)

The easiest way to get started is using Docker Compose for the database:

### 1. Start PostgreSQL with Docker

```bash
docker-compose up -d
```

This will start a PostgreSQL container with the inventory database already configured.

### 2. Verify Database is Running

```bash
docker ps
docker exec -it inventory-postgres psql -U postgres -d inventorydb -c '\dt'
```

### 3. Build and Run the Application

```bash
# Build the application
mvn clean package

# Run with Liberty Maven Plugin
mvn liberty:dev
```

The application will be available at:
- Web UI: http://localhost:9080
- API: http://localhost:9080/api/products

### 4. Stop the Services

```bash
# Stop the application (Ctrl+C in the terminal)
# Stop the database
docker-compose down
```

## Manual PostgreSQL Setup

If you prefer to install PostgreSQL manually:

### 1. Install PostgreSQL

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

**macOS (with Homebrew):**
```bash
brew install postgresql@15
brew services start postgresql@15
```

**Windows:**
Download and install from https://www.postgresql.org/download/windows/

### 2. Create Database and User

```bash
# Connect to PostgreSQL
sudo -u postgres psql

# Run the init script
\i database/init.sql

# Or manually:
CREATE DATABASE inventorydb;
GRANT ALL PRIVILEGES ON DATABASE inventorydb TO postgres;
\c inventorydb;
GRANT ALL ON SCHEMA public TO postgres;
```

### 3. Verify Connection

```bash
psql -U postgres -d inventorydb -c "SELECT version();"
```

## Open Liberty Setup

### Option 1: Using Liberty Maven Plugin (Recommended)

The Liberty Maven Plugin will automatically download and configure Open Liberty:

```bash
mvn liberty:dev
```

Features of `liberty:dev`:
- Automatic server download and setup
- Hot code replacement
- Automatic restart on changes
- Available at http://localhost:9080

### Option 2: Manual Liberty Installation

1. **Download Open Liberty**
   ```bash
   wget https://public.dhe.ibm.com/ibmdl/export/pub/software/openliberty/runtime/release/23.0.0.12/openliberty-23.0.0.12.zip
   unzip openliberty-23.0.0.12.zip -d /opt/
   ```

2. **Setup Environment**
   ```bash
   export LIBERTY_HOME=/opt/wlp
   export PATH=$LIBERTY_HOME/bin:$PATH
   ```

3. **Create Server**
   ```bash
   server create inventoryServer
   ```

4. **Copy Configuration**
   ```bash
   cp src/main/liberty/config/server.xml $LIBERTY_HOME/usr/servers/inventoryServer/
   ```

5. **Copy PostgreSQL Driver**
   ```bash
   mkdir -p $LIBERTY_HOME/usr/shared/resources/postgresql
   cp ~/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar \
      $LIBERTY_HOME/usr/shared/resources/postgresql/
   ```

6. **Deploy Application**
   ```bash
   cp target/inventory-control.war $LIBERTY_HOME/usr/servers/inventoryServer/dropins/
   ```

7. **Start Server**
   ```bash
   server start inventoryServer
   
   # View logs
   tail -f $LIBERTY_HOME/usr/servers/inventoryServer/logs/messages.log
   ```

8. **Stop Server**
   ```bash
   server stop inventoryServer
   ```

## Production Deployment

### 1. Build for Production

```bash
mvn clean package -DskipTests
```

### 2. Environment Configuration

Update `src/main/liberty/config/server.xml` with production values:

```xml
<properties.postgresql 
    serverName="${env.DB_HOST}"
    portNumber="${env.DB_PORT}"
    databaseName="${env.DB_NAME}"
    user="${env.DB_USER}"
    password="${env.DB_PASSWORD}"/>
```

Set environment variables:
```bash
export DB_HOST=your-db-host
export DB_PORT=5432
export DB_NAME=inventorydb
export DB_USER=your-user
export DB_PASSWORD=your-password
```

### 3. Security Hardening

1. **Use HTTPS**: Update `server.xml` to use SSL/TLS
2. **Database Security**: Use strong passwords and restrict network access
3. **Application Security**: Configure authentication and authorization
4. **Update Dependencies**: Regularly update all dependencies

### 4. Performance Tuning

Update JVM options in `src/main/liberty/config/jvm.options`:

```
-Xms512m
-Xmx2048m
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200
```

### 5. Monitoring

Enable MicroProfile Metrics and Health in `server.xml`:

```xml
<feature>mpMetrics-5.0</feature>
<feature>mpHealth-4.0</feature>
```

Access monitoring endpoints:
- Health: http://localhost:9080/health
- Metrics: http://localhost:9080/metrics

## Troubleshooting

### Database Connection Issues

```bash
# Check PostgreSQL is running
sudo systemctl status postgresql  # Linux
brew services list                # macOS

# Test connection
psql -U postgres -d inventorydb -c "SELECT 1;"

# Check server.xml datasource configuration
cat src/main/liberty/config/server.xml | grep -A 5 dataSource
```

### Liberty Server Issues

```bash
# Check server status
server status inventoryServer

# View logs
tail -f liberty/usr/servers/inventoryServer/logs/messages.log

# Clean and rebuild
mvn clean
rm -rf liberty/
mvn liberty:dev
```

### Port Conflicts

If port 9080 is already in use, update `server.xml`:

```xml
<httpEndpoint id="defaultHttpEndpoint"
              httpPort="8080"
              httpsPort="8443" />
```

### Common Errors

**Error: CWWKZ0002E: An exception occurred while starting the application**
- Check that PostgreSQL is running
- Verify database credentials in server.xml
- Check PostgreSQL JDBC driver is in the correct location

**Error: CWWKE0701E: FrameworkEvent ERROR**
- Check all required features are listed in server.xml
- Verify Jakarta EE 10 compatibility

**Error: Connection refused**
- Verify PostgreSQL is accepting connections
- Check firewall settings
- Ensure database host/port are correct

## Backup and Recovery

### Database Backup

```bash
# Backup
pg_dump -U postgres inventorydb > backup_$(date +%Y%m%d).sql

# Restore
psql -U postgres inventorydb < backup_20260112.sql
```

### Application Backup

```bash
# Backup configuration
tar -czf config_backup.tar.gz src/main/liberty/config/

# Backup WAR file
cp target/inventory-control.war backups/
```

## Additional Resources

- [Open Liberty Documentation](https://openliberty.io/docs/)
- [Jakarta EE Tutorial](https://eclipse-ee4j.github.io/jakartaee-tutorial/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Maven Documentation](https://maven.apache.org/guides/)
