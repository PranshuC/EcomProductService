package com.pranshu.EcomProductService.controller;

import com.pranshu.EcomProductService.dto.ProductListResponseDTO;
import com.pranshu.EcomProductService.dto.ProductRequestDTO;
import com.pranshu.EcomProductService.dto.ProductResponseDTO;
import com.pranshu.EcomProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService; // immutable - Constructor injection

    // @Autowired is optional from Spring 4.3 onwards
    public ProductController(
            @Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }

/* Field injection
    @Autowired
    @Qualifier("fakeStoreProductService")
    private ProductService productService;
 */

    @GetMapping("/products")
    public ResponseEntity getAllProducts() {
        /*ProductResponseDTO p1 = new ProductResponseDTO();
        p1.setId(1);
        p1.setTitle("iPhone 15 Pro");
        p1.setPrice(150000);
        p1.setImage("www.google.com/images/iphone");
        p1.setDescription("Kafi Mehnga phone");
        p1.setCategory("Electronics");

        ProductResponseDTO p2 = new ProductResponseDTO();
        p2.setId(2);
        p2.setTitle("Macbook Pro");
        p2.setPrice(250000);
        p2.setImage("www.google.com/images/macbook");
        p2.setDescription("Kafi Mehnga Laptop");
        p2.setCategory("Electronics");

        List<ProductResponseDTO> products = Arrays.asList(p1, p2);
        return ResponseEntity.ok(products);*/
        ProductListResponseDTO response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProductFromId(@PathVariable("id") int id) {
        ProductResponseDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO responseDTO = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id) {
        boolean response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }
}

/*
Domain Name -> IP+Port -> OS in server -> port is binded to process -> Tomat is binded to 8080
HTTP -> Tomcat -> DispatcherServlet[loads all URLs and handler mappings] -> Servlet Container -> Servlets
DispatcherServlet -> HandlerMapping -> Controller -> Service -> Repository -> DB
 */