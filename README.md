# CRUD_20260108
CRUD Inventory Control System

A modern inventory control system built with Java 17, Jakarta EE 10, PostgreSQL, and Open Liberty.

## Documentation

- [📖 Setup & Deployment Guide](DEPLOYMENT.md) - Detailed deployment instructions
- [🔌 API Documentation](API.md) - Complete REST API reference
- [🤝 Contributing Guide](CONTRIBUTING.md) - How to contribute to the project

## Features

- ✅ **CRUD Operations**: Create, Read, Update, and Delete products
- 📦 **Inventory Management**: Track product name, SKU, quantity, price, category, and description
- 🎨 **Modern UI**: Responsive web interface with real-time updates
- 🔄 **RESTful API**: JSON-based REST API for all operations
- 🗄️ **PostgreSQL Database**: Reliable persistent storage
- 🚀 **Open Liberty**: Lightweight Jakarta EE runtime

## Technology Stack

- **Java**: 17 (Java 21+ compatible)
- **Jakarta EE**: 10.0.0
- **Application Server**: Open Liberty
- **Database**: PostgreSQL 15+
- **Build Tool**: Maven
- **ORM**: Jakarta Persistence (JPA)
- **REST**: Jakarta RESTful Web Services (JAX-RS)
- **CDI**: Jakarta Contexts and Dependency Injection

## Prerequisites

- Java 17 or higher (Java 21 recommended for production)
- Maven 3.8+
- PostgreSQL 15+
- Open Liberty (will be downloaded by Maven plugin)

## Project Structure

```
CRUD_20260108/
├── src/
│   ├── main/
│   │   ├── java/com/inventory/
│   │   │   ├── model/          # JPA entities
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── service/        # Business logic
│   │   │   └── rest/           # REST API endpoints
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml
│   │   ├── webapp/
│   │   │   ├── WEB-INF/
│   │   │   ├── css/
│   │   │   ├── js/
│   │   │   └── index.html
│   │   └── liberty/config/
│   │       └── server.xml
├── database/
│   └── init.sql
└── pom.xml
```

## Setup Instructions

For complete deployment instructions including Docker setup, manual PostgreSQL installation, and production deployment, see [DEPLOYMENT.md](DEPLOYMENT.md).

### Quick Start

1. **Start PostgreSQL with Docker:**
   ```bash
   docker-compose up -d
   ```

2. **Build and Run:**
   ```bash
   mvn liberty:dev
   ```

3. **Access the Application:**
   - Web UI: http://localhost:9080
   - REST API: http://localhost:9080/api/products

### 1. Database Setup

First, ensure PostgreSQL is running, then create the database:

```bash
# Connect to PostgreSQL
psql -U postgres

# Run the initialization script
\i database/init.sql

# Or create manually:
CREATE DATABASE inventorydb;
GRANT ALL PRIVILEGES ON DATABASE inventorydb TO postgres;
```

### 2. Configure Database Connection

Update `src/main/liberty/config/server.xml` if you need different database credentials:

```xml
<properties.postgresql 
    serverName="localhost"
    portNumber="5432"
    databaseName="inventorydb"
    user="postgres"
    password="postgres"/>
```

### 3. Build the Application

```bash
mvn clean package
```

### 4. Setup Open Liberty

Create the required directories and copy the PostgreSQL driver:

```bash
# Create Liberty shared resources directory
mkdir -p liberty/usr/shared/resources/postgresql

# Download PostgreSQL JDBC driver (or copy from Maven repository)
# The driver will be in ~/.m2/repository/org/postgresql/postgresql/42.7.3/
cp ~/.m2/repository/org/postgresql/postgresql/42.7.3/postgresql-42.7.3.jar \
   liberty/usr/shared/resources/postgresql/
```

### 5. Run the Application

```bash
mvn liberty:dev
```

The application will start on:
- **Web UI**: http://localhost:9080
- **REST API**: http://localhost:9080/api/products

## Usage

### Web Interface

Open your browser and navigate to `http://localhost:9080`

The web interface allows you to:
- Add new products with name, SKU, category, quantity, price, and description
- View all products in a sortable table
- Search products by name, SKU, category, or description
- Edit existing products
- Delete products
- See real-time product count

### REST API Endpoints

#### Get all products
```bash
curl http://localhost:9080/api/products
```

#### Get product by ID
```bash
curl http://localhost:9080/api/products/1
```

#### Get product by SKU
```bash
curl http://localhost:9080/api/products/sku/ABC123
```

#### Get products by category
```bash
curl http://localhost:9080/api/products/category/Electronics
```

#### Create a new product
```bash
curl -X POST http://localhost:9080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "sku": "LAP001",
    "description": "High-performance laptop",
    "quantity": 10,
    "price": 999.99,
    "category": "Electronics"
  }'
```

#### Update a product
```bash
curl -X PUT http://localhost:9080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Pro",
    "sku": "LAP001",
    "description": "Updated laptop",
    "quantity": 15,
    "price": 1099.99,
    "category": "Electronics"
  }'
```

#### Delete a product
```bash
curl -X DELETE http://localhost:9080/api/products/1
```

#### Get product count
```bash
curl http://localhost:9080/api/products/count
```

## Development

### Running in Dev Mode

```bash
mvn liberty:dev
```

This enables:
- Hot reload of code changes
- Automatic recompilation
- Debug port on 7777

### Building for Production

```bash
mvn clean package
```

The WAR file will be created in `target/inventory-control.war`

## Database Schema

The application automatically creates the following table structure:

**products**
- `id` (BIGINT, Primary Key, Auto-increment)
- `name` (VARCHAR(100), NOT NULL)
- `description` (VARCHAR(500))
- `sku` (VARCHAR(50), NOT NULL, UNIQUE)
- `quantity` (INTEGER, NOT NULL)
- `price` (DECIMAL(10,2), NOT NULL)
- `category` (VARCHAR(50))
- `created_at` (TIMESTAMP, NOT NULL)
- `updated_at` (TIMESTAMP)

## Troubleshooting

### Database Connection Issues
- Ensure PostgreSQL is running: `sudo systemctl status postgresql`
- Check database exists: `psql -U postgres -l`
- Verify credentials in `server.xml`

### Port Already in Use
- Change ports in `src/main/liberty/config/server.xml`
- Default HTTP port: 9080
- Default HTTPS port: 9443

### Build Errors
- Ensure Java 17 or higher is installed: `java -version`
- Clean Maven cache: `mvn clean`
- Update dependencies: `mvn clean install -U`

## License

This project is open source and available under the MIT License.
