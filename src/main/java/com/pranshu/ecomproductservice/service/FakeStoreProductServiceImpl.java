package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.client.FakeStoreAPIClient;
import com.pranshu.ecomproductservice.dto.*;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pranshu.ecomproductservice.mapper.ProductMapper.fakeStoreProductResponseToProductResponse;
import static com.pranshu.ecomproductservice.mapper.ProductMapper.productRequestToFakeStoreProductRequest;
import static com.pranshu.ecomproductservice.util.ProductUtils.isNull;

@Primary
@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService {

    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreAPIClient fakeStoreAPIClient;
    private RedisTemplate<String, FakeStoreProductResponseDTO> redisTemplate;

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder, FakeStoreAPIClient fakeStoreAPIClient,
                                       RedisTemplate redisTemplate) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreAPIClient = fakeStoreAPIClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ProductListResponseDTO getAllProducts() {
        List<FakeStoreProductResponseDTO> fakeStoreProductDTOList = fakeStoreAPIClient.getAllProducts();
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO();
        for(FakeStoreProductResponseDTO fakeStoreProductDTO : fakeStoreProductDTOList) {
            productListResponseDTO.getProducts()
                    .add(fakeStoreProductResponseToProductResponse(fakeStoreProductDTO));
        }
        return productListResponseDTO;
    }

    @Override
    public ProductResponseDTO getProductById(int id) throws ProductNotFoundException {
        // Check if the product is available in Redis & return from there
        // Search with "id" in "PRODUCTS" map within Redis
        FakeStoreProductResponseDTO fakeStoreProductDto = (FakeStoreProductResponseDTO) redisTemplate.opsForHash().get("PRODUCTS", id);
        if (!isNull(fakeStoreProductDto)) {
            return fakeStoreProductResponseToProductResponse(fakeStoreProductDto);
        }

        // Else, make call to FakeStore API & fetch the product
        fakeStoreProductDto = fakeStoreAPIClient.getProductById(id);
        if(isNull(fakeStoreProductDto)) {
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }
        // Store in Redis & return
        redisTemplate.opsForHash().put("PRODUCTS", id, fakeStoreProductDto);
        return fakeStoreProductResponseToProductResponse(fakeStoreProductDto);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO =
                productRequestToFakeStoreProductRequest(productRequestDTO);
        FakeStoreProductResponseDTO fakeStoreProductDTO =
                fakeStoreAPIClient.createProduct(fakeStoreProductRequestDTO);
        return fakeStoreProductResponseToProductResponse(fakeStoreProductDTO);
    }

    @Override
    public Product updateProduct(int id, Product updatedProduct) {
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        fakeStoreAPIClient.deleteProduct(id);
        return true;
    }

    @Override
    public ProductResponseDTO findProductByTitle(String title) {
        return null;
    }
}
