package com.pranshu.ecomproductservice.mapper;

import com.pranshu.ecomproductservice.dto.*;
import com.pranshu.ecomproductservice.model.Product;

import java.util.List;
import java.util.UUID;

public class ProductMapper {
    public static FakeStoreProductRequestDTO productRequestToFakeStoreProductRequest(ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = new FakeStoreProductRequestDTO();
        fakeStoreProductRequestDTO.setTitle(productRequestDTO.getTitle());
        fakeStoreProductRequestDTO.setPrice(productRequestDTO.getPrice());
        fakeStoreProductRequestDTO.setCategory(productRequestDTO.getCategory());
        fakeStoreProductRequestDTO.setDescription(productRequestDTO.getDescription());
        fakeStoreProductRequestDTO.setImage(productRequestDTO.getImage());
        return fakeStoreProductRequestDTO;
    }

    public static ProductResponseDTO fakeStoreProductResponseToProductResponse(FakeStoreProductResponseDTO fakeStoreProductResponseDTO) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.randomUUID());
        productResponseDTO.setTitle(fakeStoreProductResponseDTO.getTitle());
        productResponseDTO.setPrice(fakeStoreProductResponseDTO.getPrice());
        productResponseDTO.setCategory(fakeStoreProductResponseDTO.getCategory());
        productResponseDTO.setDescription(fakeStoreProductResponseDTO.getDescription());
        productResponseDTO.setImage(fakeStoreProductResponseDTO.getImage());
        return productResponseDTO;
    }

    public static ProductListResponseDTO convertProductsToProductListResponseDTO(List<Product> products) {
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO();
        for(Product product : products) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setId(product.getId());
            productResponseDTO.setTitle(product.getTitle());
            productResponseDTO.setPrice(product.getPrice().getAmount());
            productResponseDTO.setCategory(product.getCategory().getCategoryName());
            productResponseDTO.setDescription(product.getDescription());
            productResponseDTO.setImage(product.getImage());
            productListResponseDTO.getProducts().add(productResponseDTO);
        }
        return productListResponseDTO;
    }

    public static ProductResponseDTO convertProductToProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setTitle(product.getTitle());
        productResponseDTO.setPrice(product.getPrice().getAmount());
        productResponseDTO.setCategory(product.getCategory().getCategoryName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setImage(product.getImage());
        return productResponseDTO;
    }
}
