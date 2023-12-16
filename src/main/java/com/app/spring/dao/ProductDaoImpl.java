package com.app.spring.dao;

import com.app.spring.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// The @Repository annotation is a specialization of the @Component annotation in Spring.
// It indicates that this class is a Data Access Object (DAO) and is responsible for encapsulating the interaction with the database or other data sources.
// Using @Repository helps in translating JDBC SQLExceptions into Spring's DataAccessException hierarchy.
@Repository
public class ProductDaoImpl implements ProductDao {
    private EntityManager entityManager;

    @Autowired
    public ProductDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
//        Updates the product entity in the database using the merge operation.
//        This method can also be used for upsert (update and insert) scenarios.
        entityManager.merge(product);
    }

    @Override
    public void updateStock(Integer stock, String id) {
//        Updates the available stock of a product in the database using a JPQL query.
        Query query = entityManager.createQuery("UPDATE Product " +
                "SET availableStock = :stock " +
                "WHERE id = :id");
        query.setParameter("stock", stock);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Product findById(String id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        // In JPQL Query, use the entity name, instead of the table name
        // In JPQL Query, use the field of JPA name, instead of the column name
        TypedQuery<Product> typedQuery = entityManager.createQuery("FROM Product", Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<Product> findAllAvailableProducts() {
        TypedQuery<Product> typedQuery = entityManager.createQuery("FROM Product " +
                "WHERE availableStock > :minimumStock ", Product.class);
        typedQuery.setParameter("minimumStock", 0);
        return typedQuery.getResultList();
    }

    @Override
    public void delete(Product product) {
        entityManager.remove(product);
    }
}
