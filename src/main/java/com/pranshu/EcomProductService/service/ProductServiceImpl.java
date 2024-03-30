package com.pranshu.EcomProductService.service;

import com.pranshu.EcomProductService.dto.ProductListResponseDTO;
import com.pranshu.EcomProductService.dto.ProductResponseDTO;
import com.pranshu.EcomProductService.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductListResponseDTO getAllProducts() {
        return null;
    }

    @Override
    public ProductResponseDTO getProductById(int id) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(int id, Product updatedProduct) {
        return null;
    }

    @Override
    public void deleteProduct(int id) {

    }
}
