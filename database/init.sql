-- Database initialization script for PostgreSQL
-- This script creates the database and user for the inventory control system

-- Create database
CREATE DATABASE inventorydb;

-- Create user (if not exists)
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'postgres') THEN
      CREATE USER postgres WITH PASSWORD 'postgres';
   END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE inventorydb TO postgres;

-- Connect to the database
\c inventorydb;

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO postgres;
