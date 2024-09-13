package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.model.Product;
import com.pranshu.ecomproductservice.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> searchProducts(String query, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.findAllByTitleContainingIgnoreCase(query, pageRequest);
        List<ProductResponseDTO> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductResponseDTO.from(product));
        }
        return productDtos;
    }
}
