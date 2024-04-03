package com.pranshu.EcomProductService.repository;

public interface CustomQueries {
    String FIND_PRODUCT_BY_TITLE = "SELECT * FROM product WHERE title like :title";
}
