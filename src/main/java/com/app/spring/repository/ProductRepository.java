package com.app.spring.repository;

import com.app.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Previously, when using EntityManager, there was a lot of boilerplate code.
// With JPARepository, we only need to specify the entity class and the data type of the primary key.
// In this case, our entity is Product, and the primary key is ID, which is of type String.
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByName(String namee);
}
