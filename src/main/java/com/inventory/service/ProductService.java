package com.inventory.service;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {
    
    @Inject
    private ProductRepository productRepository;
    
    public Product createProduct(@Valid @NotNull Product product) {
        // Check if SKU already exists
        Optional<Product> existing = productRepository.findBySku(product.getSku());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists");
        }
        return productRepository.create(product);
    }
    
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }
    
    public Product updateProduct(Long id, @Valid @NotNull Product updatedProduct) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        
        Product product = existing.get();
        
        // Check if SKU is being changed and if it's already in use
        if (!product.getSku().equals(updatedProduct.getSku())) {
            Optional<Product> skuExists = productRepository.findBySku(updatedProduct.getSku());
            if (skuExists.isPresent()) {
                throw new IllegalArgumentException("Product with SKU " + updatedProduct.getSku() + " already exists");
            }
        }
        
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setSku(updatedProduct.getSku());
        product.setQuantity(updatedProduct.getQuantity());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        
        return productRepository.update(product);
    }
    
    public void deleteProduct(Long id) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        productRepository.delete(id);
    }
    
    public long getProductCount() {
        return productRepository.count();
    }
}
