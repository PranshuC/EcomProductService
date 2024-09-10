package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.client.FakeStoreAPIClient;
import com.pranshu.ecomproductservice.dto.*;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pranshu.ecomproductservice.mapper.ProductMapper.productRequestToFakeStoreProductRequest;
import static com.pranshu.ecomproductservice.mapper.ProductMapper.fakeStoreProductResponseToProductResponse;
import static com.pranshu.ecomproductservice.util.ProductUtils.isNull;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService {

    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreAPIClient fakeStoreAPIClient;

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder, FakeStoreAPIClient fakeStoreAPIClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreAPIClient = fakeStoreAPIClient;
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
        FakeStoreProductResponseDTO fakeStoreProductDTO = fakeStoreAPIClient.getProductById(id);
        if(isNull(fakeStoreProductDTO)) {
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }
        return fakeStoreProductResponseToProductResponse(fakeStoreProductDTO);
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
