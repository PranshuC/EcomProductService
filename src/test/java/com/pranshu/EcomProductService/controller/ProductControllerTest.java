package com.pranshu.EcomProductService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranshu.EcomProductService.dto.ProductListResponseDTO;
import com.pranshu.EcomProductService.dto.ProductRequestDTO;
import com.pranshu.EcomProductService.dto.ProductResponseDTO;
import com.pranshu.EcomProductService.exception.ProductNotFoundException;
import com.pranshu.EcomProductService.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // For MockMvc @MockBean is required, @Mock won't work
    @MockBean(name="productService")
    private ProductService productService;

    //UT for Controller method invoked via APIs
    @Test
    void getAllProductsReturnEmptyListWhenNoProductsAvailable() throws Exception {
        //arrange
        ProductListResponseDTO emptyProductListResponse = new ProductListResponseDTO();
        when(productService.getAllProducts()).thenReturn(emptyProductListResponse);

        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string("{\"products\":[]}"));
    }

    @Test
    void getAllProductsReturnProducts() throws Exception {
        // Arrange
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO();
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        product1.setTitle("Laptop");
        product1.setCategory("Electronics");
        product1.setDescription("Best laptop");
        product1.setPrice(1000);
        product1.setImage("someImageURL");

        productListResponseDTO.setProducts(List.of(product1));

        when(productService.getAllProducts()).thenReturn(productListResponseDTO);

        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string("{\"products\":[{\"id\":\"feecadf2-e74c-4a06-9e32-2e6d757158b2\"," +
                        "\"title\":\"Laptop\",\"price\":1000.0,\"category\":\"Electronics\",\"description\":\"Best laptop\"," +
                        "\"image\":\"someImageURL\"}]}"));
    }

    @Test
    void createProductSuccess() throws Exception {
        // Arrange
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setTitle("Laptop");
        productRequestDTO.setCategory("Electronics");
        productRequestDTO.setDescription("Best laptop");
        productRequestDTO.setPrice(1000);
        productRequestDTO.setImage("someImageURL");

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        productResponseDTO.setTitle("Laptop");
        productResponseDTO.setCategory("Electronics");
        productResponseDTO.setDescription("Best laptop");
        productResponseDTO.setPrice(1000);
        productResponseDTO.setImage("someImageURL");

        String requestJson = convertToJson(productRequestDTO);
        String responseJson = convertToJson(productResponseDTO);

        // equivalent object - registers the mock
        when(productService.createProduct(eq(productRequestDTO))).thenReturn(productResponseDTO);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is(200))
                .andExpect(content().string(responseJson));
    }

    @Test
    void deleteProductByIdSuccess() throws Exception {
        when(productService.deleteProduct(5)).thenReturn(true);
        mockMvc.perform(delete("/products/5"))
                .andExpect(status().is(200))
                .andExpect(content().string("true"));
    }

    @Test
    void findProductByIdFailure() throws Exception {
        when(productService.getProductById(1)).thenThrow(new ProductNotFoundException("Product Not Found"));
        mockMvc.perform(get("/products/1"))
                .andExpect(status().is(404))
                .andExpect(content().string("{\"message\":\"Product Not Found\",\"statusCode\":404}"));
    }

    @Test
    void findProductByIdSuccess() throws Exception {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(UUID.fromString("feecadf2-e74c-4a06-9e32-2e6d757158b2"));
        productResponseDTO.setTitle("Laptop");
        productResponseDTO.setCategory("Electronics");
        productResponseDTO.setDescription("Best laptop");
        productResponseDTO.setPrice(1000);
        productResponseDTO.setImage("someImageURL");

        String respString = convertToJson(productResponseDTO);

        when(productService.getProductById(1)).thenReturn(productResponseDTO);
        mockMvc.perform(get("/products/1"))
                .andExpect(status().is(200))
                .andExpect(content().string(respString));
    }

    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}