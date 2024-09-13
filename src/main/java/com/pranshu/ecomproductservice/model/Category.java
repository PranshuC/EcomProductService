package com.pranshu.ecomproductservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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