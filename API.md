# REST API Documentation

This document describes the REST API endpoints for the Inventory Control System.

## Base URL

```
http://localhost:9080/api
```

## Content Type

All requests and responses use `application/json` content type.

## Endpoints

### 1. Get All Products

Retrieves a list of all products in the inventory.

**Endpoint:** `GET /products`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "description": "High-performance laptop",
    "sku": "LAP001",
    "quantity": 10,
    "price": 999.99,
    "category": "Electronics",
    "createdAt": "2026-01-12T10:30:00",
    "updatedAt": "2026-01-12T10:30:00"
  },
  {
    "id": 2,
    "name": "Mouse",
    "description": "Wireless mouse",
    "sku": "MOU001",
    "quantity": 50,
    "price": 29.99,
    "category": "Electronics",
    "createdAt": "2026-01-12T11:00:00",
    "updatedAt": "2026-01-12T11:00:00"
  }
]
```

---

### 2. Get Product by ID

Retrieves a specific product by its ID.

**Endpoint:** `GET /products/{id}`

**Parameters:**
- `id` (path parameter) - Product ID

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "sku": "LAP001",
  "quantity": 10,
  "price": 999.99,
  "category": "Electronics",
  "createdAt": "2026-01-12T10:30:00",
  "updatedAt": "2026-01-12T10:30:00"
}
```

**Error Response:** `404 Not Found`
```json
{
  "error": "Product not found"
}
```

---

### 3. Get Product by SKU

Retrieves a product by its SKU (Stock Keeping Unit).

**Endpoint:** `GET /products/sku/{sku}`

**Parameters:**
- `sku` (path parameter) - Product SKU

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "sku": "LAP001",
  "quantity": 10,
  "price": 999.99,
  "category": "Electronics",
  "createdAt": "2026-01-12T10:30:00",
  "updatedAt": "2026-01-12T10:30:00"
}
```

**Error Response:** `404 Not Found`
```json
{
  "error": "Product not found"
}
```

---

### 4. Get Products by Category

Retrieves all products in a specific category.

**Endpoint:** `GET /products/category/{category}`

**Parameters:**
- `category` (path parameter) - Product category

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "sku": "LAP001",
    "quantity": 10,
    "price": 999.99,
    "category": "Electronics"
  }
]
```

---

### 5. Create Product

Creates a new product in the inventory.

**Endpoint:** `POST /products`

**Request Body:**
```json
{
  "name": "Keyboard",
  "description": "Mechanical keyboard",
  "sku": "KEY001",
  "quantity": 25,
  "price": 79.99,
  "category": "Electronics"
}
```

**Validation Rules:**
- `name`: Required, 1-100 characters
- `sku`: Required, unique, 1-50 characters
- `quantity`: Required, >= 0
- `price`: Required, >= 0.0
- `description`: Optional, max 500 characters
- `category`: Optional, max 50 characters

**Response:** `201 Created`
```json
{
  "id": 3,
  "name": "Keyboard",
  "description": "Mechanical keyboard",
  "sku": "KEY001",
  "quantity": 25,
  "price": 79.99,
  "category": "Electronics",
  "createdAt": "2026-01-12T14:00:00",
  "updatedAt": "2026-01-12T14:00:00"
}
```

**Error Response:** `400 Bad Request`
```json
{
  "error": "Product with SKU KEY001 already exists"
}
```

---

### 6. Update Product

Updates an existing product.

**Endpoint:** `PUT /products/{id}`

**Parameters:**
- `id` (path parameter) - Product ID

**Request Body:**
```json
{
  "name": "Keyboard Pro",
  "description": "Premium mechanical keyboard",
  "sku": "KEY001",
  "quantity": 30,
  "price": 89.99,
  "category": "Electronics"
}
```

**Response:** `200 OK`
```json
{
  "id": 3,
  "name": "Keyboard Pro",
  "description": "Premium mechanical keyboard",
  "sku": "KEY001",
  "quantity": 30,
  "price": 89.99,
  "category": "Electronics",
  "createdAt": "2026-01-12T14:00:00",
  "updatedAt": "2026-01-12T15:30:00"
}
```

**Error Response:** `404 Not Found`
```json
{
  "error": "Product with ID 999 not found"
}
```

**Error Response:** `400 Bad Request`
```json
{
  "error": "Product with SKU KEY001 already exists"
}
```

---

### 7. Delete Product

Deletes a product from the inventory.

**Endpoint:** `DELETE /products/{id}`

**Parameters:**
- `id` (path parameter) - Product ID

**Response:** `204 No Content`

**Error Response:** `404 Not Found`
```json
{
  "error": "Product with ID 999 not found"
}
```

---

### 8. Get Product Count

Returns the total number of products in the inventory.

**Endpoint:** `GET /products/count`

**Response:** `200 OK`
```json
{
  "count": 42
}
```

---

## Example Usage

### cURL Examples

**Get all products:**
```bash
curl -X GET http://localhost:9080/api/products
```

**Create a product:**
```bash
curl -X POST http://localhost:9080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "USB Cable",
    "sku": "USB001",
    "description": "USB-C to USB-A cable",
    "quantity": 100,
    "price": 9.99,
    "category": "Accessories"
  }'
```

**Update a product:**
```bash
curl -X PUT http://localhost:9080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Pro",
    "sku": "LAP001",
    "description": "High-performance laptop with upgrades",
    "quantity": 15,
    "price": 1199.99,
    "category": "Electronics"
  }'
```

**Delete a product:**
```bash
curl -X DELETE http://localhost:9080/api/products/1
```

**Get product by SKU:**
```bash
curl -X GET http://localhost:9080/api/products/sku/LAP001
```

**Get products by category:**
```bash
curl -X GET http://localhost:9080/api/products/category/Electronics
```

### JavaScript/Fetch Examples

**Get all products:**
```javascript
fetch('http://localhost:9080/api/products')
  .then(response => response.json())
  .then(products => console.log(products))
  .catch(error => console.error('Error:', error));
```

**Create a product:**
```javascript
fetch('http://localhost:9080/api/products', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    name: 'USB Cable',
    sku: 'USB001',
    description: 'USB-C to USB-A cable',
    quantity: 100,
    price: 9.99,
    category: 'Accessories'
  })
})
  .then(response => response.json())
  .then(product => console.log('Created:', product))
  .catch(error => console.error('Error:', error));
```

**Update a product:**
```javascript
fetch('http://localhost:9080/api/products/1', {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    name: 'Laptop Pro',
    sku: 'LAP001',
    description: 'High-performance laptop with upgrades',
    quantity: 15,
    price: 1199.99,
    category: 'Electronics'
  })
})
  .then(response => response.json())
  .then(product => console.log('Updated:', product))
  .catch(error => console.error('Error:', error));
```

**Delete a product:**
```javascript
fetch('http://localhost:9080/api/products/1', {
  method: 'DELETE'
})
  .then(() => console.log('Deleted successfully'))
  .catch(error => console.error('Error:', error));
```

## Error Handling

All error responses follow this format:
```json
{
  "error": "Description of the error"
}
```

### HTTP Status Codes

- `200 OK` - Request succeeded
- `201 Created` - Resource created successfully
- `204 No Content` - Resource deleted successfully
- `400 Bad Request` - Invalid request data or validation error
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Data Models

### Product

| Field | Type | Required | Constraints | Description |
|-------|------|----------|-------------|-------------|
| id | Long | Auto | - | Unique identifier (auto-generated) |
| name | String | Yes | 1-100 chars | Product name |
| description | String | No | Max 500 chars | Product description |
| sku | String | Yes | 1-50 chars, unique | Stock Keeping Unit |
| quantity | Integer | Yes | >= 0 | Available quantity |
| price | BigDecimal | Yes | >= 0.0 | Product price |
| category | String | No | Max 50 chars | Product category |
| createdAt | LocalDateTime | Auto | - | Creation timestamp |
| updatedAt | LocalDateTime | Auto | - | Last update timestamp |

## Best Practices

1. **Always validate input data** before sending requests
2. **Handle errors gracefully** with proper error messages
3. **Use appropriate HTTP methods** (GET for read, POST for create, PUT for update, DELETE for delete)
4. **Check status codes** to determine success or failure
5. **Use unique SKUs** to avoid conflicts
6. **Keep quantity non-negative** to maintain data integrity

## Rate Limiting

Currently, there are no rate limits implemented. For production use, consider implementing rate limiting to prevent abuse.

## Authentication

Currently, the API does not require authentication. For production use, implement proper authentication and authorization mechanisms.
