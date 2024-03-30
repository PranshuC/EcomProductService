package com.pranshu.EcomProductService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;
}
/*
    Product - Category : M : 1
    1         1
    M         1
    M         1

    Category
        List<Product>

    Product
        Category

    1 > M
 */