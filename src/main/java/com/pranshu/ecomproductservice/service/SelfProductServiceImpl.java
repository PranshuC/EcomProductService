package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.dto.ProductListResponseDTO;
import com.pranshu.ecomproductservice.dto.ProductRequestDTO;
import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.dto.UserDto;
import com.pranshu.ecomproductservice.exception.InvalidTitleException;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.Category;
import com.pranshu.ecomproductservice.model.Price;
import com.pranshu.ecomproductservice.model.Product;
import com.pranshu.ecomproductservice.repository.CategoryRepository;
import com.pranshu.ecomproductservice.repository.PriceRepository;
import com.pranshu.ecomproductservice.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.pranshu.ecomproductservice.mapper.ProductMapper.convertProductToProductResponseDTO;
import static com.pranshu.ecomproductservice.mapper.ProductMapper.convertProductsToProductListResponseDTO;

@Primary
@Service("productService")
public class SelfProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private PriceRepository priceRepository;
    private CategoryRepository categoryRepository;
    //private OpenSearchProductRepository openSearchProductRepository;
    private RestTemplate restTemplate;


    public SelfProductServiceImpl(ProductRepository productRepository, PriceRepository priceRepository,
                                  CategoryRepository categoryRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.categoryRepository = categoryRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductListResponseDTO getAllProducts() {
        List<Product> products = productRepository.findAll();
        return convertProductsToProductListResponseDTO(products);
    }

    @Override
    public ProductResponseDTO getProductById(int id) {
        // Actual implementation : id is UUID, so skipping for now

        // User Service Call - Test Eureka Server
        //String URL = "http://localhost:9090/users/1";
        String URL = "http://ecomuserservice/users/1";
        ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(URL, UserDto.class);

        return new ProductResponseDTO();
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        // Convert ProductRequestDTO to Product entity
        Product product = new Product();
        product.setTitle(productRequestDTO.getTitle());
        product.setDescription(productRequestDTO.getDescription());
        product.setImage(productRequestDTO.getImage());
        Price price = priceRepository.save(new Price("INR", productRequestDTO.getPrice(), 0.0));
        product.setPrice(price);
        Category category = categoryRepository.save(new Category(productRequestDTO.getCategory()));
        product.setCategory(category);

        // Save the product entity
        Product savedProduct = productRepository.save(product);

        // Save the product entity in OpenSearch after Id generated in PostgreSQL
        //openSearchProductRepository.save(savedProduct);

        // Convert the saved Product entity to ProductResponseDTO
        ProductResponseDTO productResponseDTO = ProductResponseDTO.from(savedProduct);

        return productResponseDTO;
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
