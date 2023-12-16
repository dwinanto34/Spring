package com.app.spring.implementation;

import com.app.spring.dao.ProductDao;
import com.app.spring.entity.Product;
import com.app.spring.repository.ProductRepository;
import com.app.spring.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service annotation is a specialization of @Component,
// It allows for better organization and differentiation between different types of components within the application
// Enhancing maintainability and clarity in the overall architecture.
@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductRepository productRepository) {
        this.productDao = productDao;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
//        Using EntityManager
//        return productDao.findAll();

//        Using JPARepository
        return productRepository.findAll();
    }

    @Override
    public Product findById(String id) {
//        Using EntityManager
//        return productDao.findById(id);

//        Using JPARepository
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    @Override
//    Transactional -- All or Nothing
    @Transactional
    public void save(Product product) {
//        Using EntityManager
//        productDao.save(product);

//        Using JPARepository
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void update(Product product) {
//        Using EntityManager
//        productDao.update(product);

//        Using JPARepository
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(String id) {
//        Using EntityManager
//        Product product = findById(id);
//        productDao.delete(product);

//        Using JPARepository
        productRepository.deleteById(id);
    }
}
