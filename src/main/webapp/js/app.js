// API Base URL
const API_URL = '/api/products';

// State
let products = [];
let editingProductId = null;

// DOM Elements
const productForm = document.getElementById('productForm');
const productsTableBody = document.getElementById('productsTableBody');
const loadingDiv = document.getElementById('loading');
const errorDiv = document.getElementById('error');
const searchInput = document.getElementById('searchInput');
const totalProductsSpan = document.getElementById('totalProducts');
const formTitle = document.getElementById('formTitle');
const submitBtn = document.getElementById('submitBtn');
const cancelBtn = document.getElementById('cancelBtn');

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
    
    productForm.addEventListener('submit', handleSubmit);
    cancelBtn.addEventListener('click', resetForm);
    searchInput.addEventListener('input', handleSearch);
});

// Load all products
async function loadProducts() {
    try {
        showLoading(true);
        hideError();
        
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error('Failed to load products');
        }
        
        products = await response.json();
        renderProducts(products);
        updateStats();
        showLoading(false);
    } catch (error) {
        console.error('Error loading products:', error);
        showError('Failed to load products. Please try again.');
        showLoading(false);
    }
}

// Render products in table
function renderProducts(productsToRender) {
    productsTableBody.innerHTML = '';
    
    if (productsToRender.length === 0) {
        productsTableBody.innerHTML = `
            <tr>
                <td colspan="8" style="text-align: center; padding: 40px; color: #6c757d;">
                    No products found. Add your first product to get started!
                </td>
            </tr>
        `;
        return;
    }
    
    productsToRender.forEach(product => {
        const row = document.createElement('tr');
        
        const quantityClass = product.quantity === 0 ? 'quantity-low' : 
                             product.quantity < 10 ? 'quantity-medium' : 'quantity-high';
        
        row.innerHTML = `
            <td>${product.id}</td>
            <td><strong>${escapeHtml(product.name)}</strong></td>
            <td><code>${escapeHtml(product.sku)}</code></td>
            <td>${product.category ? escapeHtml(product.category) : '-'}</td>
            <td><span class="quantity-badge ${quantityClass}">${product.quantity}</span></td>
            <td class="price">$${parseFloat(product.price).toFixed(2)}</td>
            <td>${product.description ? escapeHtml(product.description) : '-'}</td>
            <td class="actions">
                <button class="btn-edit" onclick="editProduct(${product.id})">Edit</button>
                <button class="btn-delete" onclick="deleteProduct(${product.id})">Delete</button>
            </td>
        `;
        
        productsTableBody.appendChild(row);
    });
}

// Handle form submission
async function handleSubmit(e) {
    e.preventDefault();
    
    const product = {
        name: document.getElementById('name').value,
        sku: document.getElementById('sku').value,
        category: document.getElementById('category').value || null,
        quantity: parseInt(document.getElementById('quantity').value),
        price: parseFloat(document.getElementById('price').value),
        description: document.getElementById('description').value || null
    };
    
    try {
        let response;
        if (editingProductId) {
            // Update existing product
            response = await fetch(`${API_URL}/${editingProductId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(product)
            });
        } else {
            // Create new product
            response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(product)
            });
        }
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.error || 'Failed to save product');
        }
        
        resetForm();
        loadProducts();
        showSuccess(editingProductId ? 'Product updated successfully!' : 'Product created successfully!');
    } catch (error) {
        console.error('Error saving product:', error);
        showError(error.message);
    }
}

// Edit product
async function editProduct(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) {
            throw new Error('Failed to load product');
        }
        
        const product = await response.json();
        
        document.getElementById('productId').value = product.id;
        document.getElementById('name').value = product.name;
        document.getElementById('sku').value = product.sku;
        document.getElementById('category').value = product.category || '';
        document.getElementById('quantity').value = product.quantity;
        document.getElementById('price').value = product.price;
        document.getElementById('description').value = product.description || '';
        
        editingProductId = id;
        formTitle.textContent = 'Edit Product';
        submitBtn.textContent = 'Update Product';
        cancelBtn.style.display = 'inline-block';
        
        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    } catch (error) {
        console.error('Error loading product:', error);
        showError('Failed to load product for editing.');
    }
}

// Delete product
async function deleteProduct(id) {
    if (!confirm('Are you sure you want to delete this product?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error('Failed to delete product');
        }
        
        loadProducts();
        showSuccess('Product deleted successfully!');
    } catch (error) {
        console.error('Error deleting product:', error);
        showError('Failed to delete product.');
    }
}

// Reset form
function resetForm() {
    productForm.reset();
    document.getElementById('productId').value = '';
    editingProductId = null;
    formTitle.textContent = 'Add New Product';
    submitBtn.textContent = 'Add Product';
    cancelBtn.style.display = 'none';
}

// Handle search
function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    
    const filtered = products.filter(product => 
        product.name.toLowerCase().includes(searchTerm) ||
        product.sku.toLowerCase().includes(searchTerm) ||
        (product.category && product.category.toLowerCase().includes(searchTerm)) ||
        (product.description && product.description.toLowerCase().includes(searchTerm))
    );
    
    renderProducts(filtered);
}

// Update statistics
function updateStats() {
    totalProductsSpan.textContent = products.length;
}

// Show/hide loading
function showLoading(show) {
    loadingDiv.style.display = show ? 'block' : 'none';
}

// Show error message
function showError(message) {
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
    setTimeout(() => {
        errorDiv.style.display = 'none';
    }, 5000);
}

// Hide error message
function hideError() {
    errorDiv.style.display = 'none';
}

// Show success message
function showSuccess(message) {
    const successDiv = document.createElement('div');
    successDiv.className = 'error';
    successDiv.style.background = '#d4edda';
    successDiv.style.color = '#155724';
    successDiv.textContent = message;
    
    const listSection = document.querySelector('.list-section');
    listSection.insertBefore(successDiv, listSection.firstChild);
    
    setTimeout(() => {
        successDiv.remove();
    }, 3000);
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
