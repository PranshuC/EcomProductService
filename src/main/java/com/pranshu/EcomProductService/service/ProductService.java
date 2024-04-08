package com.pranshu.EcomProductService.service;

import com.pranshu.EcomProductService.dto.ProductListResponseDTO;
import com.pranshu.EcomProductService.dto.ProductRequestDTO;
import com.pranshu.EcomProductService.dto.ProductResponseDTO;
import com.pranshu.EcomProductService.exception.ProductNotFoundException;
import com.pranshu.EcomProductService.model.Product;

import java.util.List;

public interface ProductService {
    public ProductListResponseDTO getAllProducts();
    public ProductResponseDTO getProductById(int id) throws ProductNotFoundException;
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    public Product updateProduct(int id, Product updatedProduct);
    public boolean deleteProduct(int id);
    public ProductResponseDTO findProductByTitle(String title) throws ProductNotFoundException;
}
