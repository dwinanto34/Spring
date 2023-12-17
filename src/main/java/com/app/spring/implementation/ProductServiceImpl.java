package com.app.spring.implementation;

import com.app.spring.dao.ProductDao;
import com.app.spring.entity.Order;
import com.app.spring.entity.OrderItem;
import com.app.spring.entity.Product;
import com.app.spring.model.request.ProductRequest;
import com.app.spring.model.response.ProductResponse;
import com.app.spring.repository.OrderItemRepository;
import com.app.spring.repository.OrderRepository;
import com.app.spring.repository.ProductRepository;
import com.app.spring.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ProductResponse> findAll() {
//        Using EntityManager
//        return productDao.findAll();

//        Using JPARepository
        List<ProductResponse> responses = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductResponse response = convert(product);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public ProductResponse findById(String id) {
//        Using EntityManager
//        return productDao.findById(id);

//        Using JPARepository
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return convert(product.get());
        }

        return null;
    }

    @Override
//    Transactional -- All or Nothing
    @Transactional
    public void save(ProductRequest productRequest) {
        Product product = convert(productRequest);
//        Using EntityManager
//        productDao.save(product);

//        Using JPARepository
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void update(String id, ProductRequest productRequest) {
        Optional<Product> existingProduct = productRepository.findById(id);

        Product product = convert(productRequest);
        if (existingProduct.isPresent()) {
            product.setId(existingProduct.get().getId());
        }
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

    private Product convert(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .availableStock(productRequest.getAvailableStock())
                .build();
    }

    private ProductResponse convert(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableStock(product.getAvailableStock())
                .build();
    }
}
