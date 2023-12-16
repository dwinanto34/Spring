package com.app.spring.implementation;

import com.app.spring.dao.ProductDao;
import com.app.spring.entity.Product;
import com.app.spring.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service annotation is a specialization of @Component,
// It allows for better organization and differentiation between different types of components within the application
// Enhancing maintainability and clarity in the overall architecture.
@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(String id) {
        return productDao.findById(id);
    }

    @Override
//    Transactional -- All or Nothing
    @Transactional
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Product product = findById(id);
        productDao.delete(product);
    }
}
