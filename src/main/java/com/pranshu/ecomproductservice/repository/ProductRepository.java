package com.pranshu.ecomproductservice.repository;

import com.pranshu.ecomproductservice.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByTitle(String title);

    // SELECT * FROM product WHERE title = ? AND description = ?
    Product findByTitleAndDescription(String title, String description);

    // SELECT * FROM product WHERE title = ? OR description = ?
    Product findByTitleOrDescription(String title, String description);

    @Query(value = CustomQueries.FIND_PRODUCT_BY_TITLE, nativeQuery = true)
    Product findProductByTitleLike(String title);

    // <= price
    Product findByPrice_amountLessThanEqual(double amount);

    // > price
    //Product findByPriceGreaterThan(double price); // Price object error

    //Product findByPriceBetweenStartPriceAndEndPrice(double startPrice, double endPrice);

    List<Product> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
}

/**
 * JPA is powerful enough to generate queries based on method names
 *  JPA maps attributes of the entity to the method names
 *  Hibernate will generate the query based on attributes
 *  findByPrice_amount -> price.amount will be used to generate the query
 *
 *  Custom SQL queries can be written using @Query annotation
 */

