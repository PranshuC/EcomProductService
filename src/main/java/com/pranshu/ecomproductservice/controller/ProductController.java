package com.pranshu.ecomproductservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranshu.ecomproductservice.client.UserServiceClient;
import com.pranshu.ecomproductservice.dto.*;
import com.pranshu.ecomproductservice.exception.InvalidTokenException;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.SessionStatus;
import com.pranshu.ecomproductservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class ProductController {

    private final ProductService productService; // immutable - Constructor injection
    private final UserServiceClient userServiceClient;

    // @Autowired is optional from Spring 4.3 onwards
    //@Qualifier("fakeStoreProductService")
    //@Qualifier("productService") -> @Primary (in ProductServiceImpl)
    public ProductController(ProductService productService, UserServiceClient userServiceClient) {
        this.productService = productService;
        this.userServiceClient = userServiceClient;
    }

/* Field injection
    @Autowired
    @Qualifier("fakeStoreProductService")
    private ProductService productService;
 */

    @GetMapping("/products")
    public ResponseEntity getAllProducts(@RequestHeader("token") String token) {
        /*
        ProductResponseDTO p1 = new ProductResponseDTO();
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
        return ResponseEntity.ok(products);
        */

        validateUser(token);
        ProductListResponseDTO response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProductById(@PathVariable("id") int id, @RequestHeader("token") String token) throws ProductNotFoundException {
        ProductResponseDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/title/{title}")
    public ResponseEntity findProductByTitle(@PathVariable("title") String title, @RequestHeader("token") String token) throws ProductNotFoundException {
        ProductResponseDTO response = productService.findProductByTitle(title);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody ProductRequestDTO productRequestDTO, @RequestHeader("token") String token) {
        ProductResponseDTO responseDTO = productService.createProduct(productRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id, @RequestHeader("token") String token) {
        boolean response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }

    private void validateUser(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        JwtPayloadDTO jwtPayload = null;
        try {
            jwtPayload = mapper.readValue(payload, JwtPayloadDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int userId = jwtPayload.getUserId();
        ValidateTokenDTO validateTokenDTO = new ValidateTokenDTO(userId, token);
        String result = userServiceClient.validateToken(validateTokenDTO);
        if(!result.contains(SessionStatus.ACTIVE.name())){
            throw new InvalidTokenException("Token is not valid");
        }
    }
}

/*
Domain Name -> IP+Port -> OS in server -> port is binded to process -> Tomcat is binded to 8080
HTTP -> Tomcat -> DispatcherServlet[loads all URLs and handler mappings] -> Servlet Container -> Servlets
DispatcherServlet -> HandlerMapping -> Controller -> Service -> Repository -> DB
 */