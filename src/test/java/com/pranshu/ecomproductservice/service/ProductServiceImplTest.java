package com.pranshu.ecomproductservice.service;

import com.pranshu.ecomproductservice.dto.ProductResponseDTO;
import com.pranshu.ecomproductservice.exception.InvalidTitleException;
import com.pranshu.ecomproductservice.exception.ProductNotFoundException;
import com.pranshu.ecomproductservice.model.Category;
import com.pranshu.ecomproductservice.model.Price;
import com.pranshu.ecomproductservice.model.Product;
import com.pranshu.ecomproductservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTest {

    // We need a mock object of the given attribute
    @Mock
    private ProductRepository productRepository;

    // This is the class we want to test, and we would inject the mock object
    // We are marking now; when class is created, it will inject the mock object
    @InjectMocks
    private SelfProductServiceImpl selfProductServiceImpl;

    @BeforeEach
    public void setUp() {
        // Creates auto closeable resources for each test method
        // Best practice - create new object for every test method
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindProductByTitleSuccess() throws ProductNotFoundException {
        // Arrange
        Price mockPrice = new Price("INR", 0.0, 0.0);
        mockPrice.setAmount(100);

        Category mockCategory = new Category();
        mockCategory.setCategoryName("mockCategory");

        String testTitle = "testProductTitle";
        Product mockProduct = new Product();
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setTitle(testTitle);
        mockProduct.setDescription("testProductDescription");
        mockProduct.setPrice(mockPrice);
        mockProduct.setCategory(mockCategory);

        //when(productRepository.findByTitle(any())).thenReturn(mockProduct);
        when(productRepository.findByTitle(testTitle)).thenReturn(mockProduct);

        // Act
        ProductResponseDTO actualResponse = selfProductServiceImpl.findProductByTitle(testTitle);

        // Assert
        Assertions.assertEquals(actualResponse.getId(), mockProduct.getId());
        Assertions.assertEquals(actualResponse.getTitle(), mockProduct.getTitle());
        Assertions.assertEquals(actualResponse.getDescription(), mockProduct.getDescription());
    }

    @Test
    public void testFindProductByTitle_RepoRespondsWithNullObject() {
        // Arrange
        String testTitle = "testProductTitle";
        when(productRepository.findByTitle(testTitle)).thenReturn(null);

        // Act and Assert
        Assertions.assertThrows(ProductNotFoundException.class, () -> selfProductServiceImpl.findProductByTitle(testTitle));
    }

    @Test
    public void testFindProductByTitle_NullTitle() {
        // Arrange
        Price mockPrice = new Price("INR", 0.0, 0.0);
        mockPrice.setAmount(100);

        Category mockCategory = new Category();
        mockCategory.setCategoryName("mockCategory");

        String testTitle = null;
        Product mockProduct = new Product();
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setTitle(testTitle);
        mockProduct.setDescription("testProductDescription");
        mockProduct.setPrice(mockPrice);
        mockProduct.setCategory(mockCategory);
        when(productRepository.findByTitle(testTitle)).thenReturn(mockProduct);

        // Act and Assert
        Assertions.assertThrows(InvalidTitleException.class, () -> selfProductServiceImpl.findProductByTitle(testTitle));
    }
}
