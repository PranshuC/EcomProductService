package com.pranshu.ecomproductservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String title;
    private String description;
    private String image;
    @OneToOne
    private Price price;
    @ManyToOne
    private Category category;
}
/*
    Product - Category : M : 1
    1         1
    M         1
    -----------
    M         1

    Product - Order is ManyToMany Uni-Directional mapping
    So, only Order will have List of products
    With "ManyToMany" annotation here (Bi-Directional), Hibernate will create another table
    "ORDER_PRODUCTS" with "ECOM_ORDER_PRODUCTS" with same columns "order_id" and "product_id"

    At any single point of time, Product will have only one Price
 */