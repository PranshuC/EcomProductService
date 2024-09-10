package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.dto.ProductListResponseDTO;
import com.pranshu.ecomproductservice.dto.ProductRequestDTO;
import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.Product;

public interface ProductService {
    public ProductListResponseDTO getAllProducts();
    public ProductResponseDTO getProductById(int id) throws ProductNotFoundException;
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    public Product updateProduct(int id, Product updatedProduct);
    public boolean deleteProduct(int id);
    public ProductResponseDTO findProductByTitle(String title) throws ProductNotFoundException;
}
