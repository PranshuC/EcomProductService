package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.dto.ProductListResponseDTO;
import com.pranshu.ecomproductservice.dto.ProductRequestDTO;
import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.exception.InvalidTitleException;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.Product;
import com.pranshu.ecomproductservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pranshu.ecomproductservice.mapper.ProductMapper.convertProductsToProductListResponseDTO;
import static com.pranshu.ecomproductservice.mapper.ProductMapper.convertProductToProductResponseDTO;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public ProductListResponseDTO getAllProducts() {
        List<Product> products = productRepository.findAll();
        return convertProductsToProductListResponseDTO(products);
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
