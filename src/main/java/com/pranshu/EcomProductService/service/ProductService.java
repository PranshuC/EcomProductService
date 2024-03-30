package com.pranshu.EcomProductService.service;

import com.pranshu.EcomProductService.dto.ProductListResponseDTO;
import com.pranshu.EcomProductService.dto.ProductResponseDTO;
import com.pranshu.EcomProductService.model.Product;

import java.util.List;

public interface ProductService {
    public ProductListResponseDTO getAllProducts();
    public ProductResponseDTO getProductById(int id);
    public Product createProduct(Product product);
    public Product updateProduct(int id, Product updatedProduct);
    public void deleteProduct(int id);
}
