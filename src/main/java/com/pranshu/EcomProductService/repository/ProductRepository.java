package com.pranshu.EcomProductService.repository;

import com.pranshu.EcomProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByTitle(String title);

    // Select * from product where title = ? and description = ?
    Product findByTitleAndDescription(String title, String description);

    // Select * from product where title = ? or description = ?
    Product findByTitleOrDescription(String title, String description);

    // <= price
    Product findByPriceLessThanEqual(double price);

    // > price
    Product findByPriceGreaterThan(double price);

    Product findByPriceBetweenStartPriceAndEndPrice(double startPrice, double endPrice);
}

// JPA is powerful enough to generate queries based on method names
// JPA maps attributes of the entity to the method names
// Hibernate will generate the query based on attributes
// Custom SQL queries can be written using @Query annotation
