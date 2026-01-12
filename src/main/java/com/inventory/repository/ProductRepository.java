package com.inventory.repository;

import com.inventory.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepository {
    
    @PersistenceContext(unitName = "inventoryPU")
    private EntityManager entityManager;
    
    @Transactional
    public Product create(Product product) {
        entityManager.persist(product);
        return product;
    }
    
    public Optional<Product> findById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }
    
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p ORDER BY p.name", Product.class);
        return query.getResultList();
    }
    
    public List<Product> findByCategory(String category) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.category = :category ORDER BY p.name", Product.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
    
    public Optional<Product> findBySku(String sku) {
        TypedQuery<Product> query = entityManager.createQuery(
            "SELECT p FROM Product p WHERE p.sku = :sku", Product.class);
        query.setParameter("sku", sku);
        List<Product> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    @Transactional
    public Product update(Product product) {
        return entityManager.merge(product);
    }
    
    @Transactional
    public void delete(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
        }
    }
    
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(p) FROM Product p", Long.class);
        return query.getSingleResult();
    }
}
