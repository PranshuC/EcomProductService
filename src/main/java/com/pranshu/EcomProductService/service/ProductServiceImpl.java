package com.pranshu.EcomProductService.service;

import com.pranshu.EcomProductService.dto.ProductListResponseDTO;
import com.pranshu.EcomProductService.dto.ProductRequestDTO;
import com.pranshu.EcomProductService.dto.ProductResponseDTO;
import com.pranshu.EcomProductService.exception.InvalidTitleException;
import com.pranshu.EcomProductService.exception.ProductNotFoundException;
import com.pranshu.EcomProductService.model.Product;
import com.pranshu.EcomProductService.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pranshu.EcomProductService.mapper.ProductMapper.convertProductListToProductListResponseDTO;
import static com.pranshu.EcomProductService.mapper.ProductMapper.convertProductToProductResponseDTO;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public ProductListResponseDTO getAllProducts() {
        List<Product> products = productRepository.findAll();
        return convertProductListToProductListResponseDTO(products);
    }

    @Override
    public ProductResponseDTO getProductById(int id) {
        return null;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public Product updateProduct(int id, Product updatedProduct) {
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        return false;
    }

    @Override
    public ProductResponseDTO findProductByTitle(String title) throws ProductNotFoundException {
        if(title == null || title.isEmpty()) {
            throw new InvalidTitleException("title is invalid");
        }
        Product product = productRepository.findByTitle(title);
        if(product == null) {
            throw new ProductNotFoundException("Product with given title is not available");
        }
        ProductResponseDTO productResponseDTO = convertProductToProductResponseDTO(product);
        return productResponseDTO;
    }
}
