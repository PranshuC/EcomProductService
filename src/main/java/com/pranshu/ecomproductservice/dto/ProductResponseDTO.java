package com.pranshu.ecomproductservice.dto;

import com.pranshu.ecomproductservice.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductResponseDTO {
    private UUID id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public static ProductResponseDTO from(Product product) {
        ProductResponseDTO productDto = new ProductResponseDTO();
        if(product == null) {
            return null;
        }
        if (product.getId() != null) {
            productDto.setId(product.getId());
        }
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice().getAmount());
        productDto.setCategory(product.getCategory().getCategoryName());
        productDto.setDescription(product.getDescription());
        productDto.setImage(product.getImage());
        return productDto;
    }
}
