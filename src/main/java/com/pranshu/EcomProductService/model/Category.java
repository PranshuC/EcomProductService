package com.pranshu.EcomProductService.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String categoryName;
    //@OneToMany
    //@JoinColumn(name = "category_id")
    //private List<Product> products;
}

/*  List<Product> usecase : Zomato/Swiggy showing all foodItems under 1 type.
    Option A : Category -> List<Product>    Correct
    Option B : Product -> Category
    1 > M
    But to keep clean in DB (mapping table created instead of FK), use @JoinColumn
*/