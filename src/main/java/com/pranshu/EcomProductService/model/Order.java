package com.pranshu.EcomProductService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "ECOM_ORDER")
public class Order extends BaseModel {
    private double price;
    @ManyToMany
    private List<Product> products; // ECOM_ORDER_PRODUCTS
}

/*
    Product     Order
    1           M
    M           1
    Product - Order -> M:M

    "Order" is a keyword in PostgreSQL like "User",
    so we can't use it as a table name.
 */