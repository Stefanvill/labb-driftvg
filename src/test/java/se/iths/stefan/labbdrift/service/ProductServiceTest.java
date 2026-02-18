package se.iths.stefan.labbdrift.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.stefan.labbdrift.exception.product.ProductNotFoundException;
import se.iths.stefan.labbdrift.model.Product;
import se.iths.stefan.labbdrift.repository.ProductRepository;
import se.iths.stefan.labbdrift.validator.ProductValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private ProductValidator productValidator;

    private Product product;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        productValidator = mock(ProductValidator.class);
        productService = new ProductService(productRepository, productValidator);

        product = new Product();
    }

    @Test
    void displayProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAll();

        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void getProduct() {
        product.setId(1L);
        product.setName("Milk");
        product.setPrice(10);
        product.setStock(5);
        product.setActive(true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getOne(1L);

        assertNotNull(result);
        assertEquals("Milk", result.getName());
        verify(productRepository).findById(1L);
        assertDoesNotThrow(() -> productService.getOne(1L));
    }

    @Test
    void getProduct_whenMissing_throws() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getOne(99L));
        verify(productRepository).findById(99L);
    }

    @Test
    void createProduct() {
        product.setName("Milk");
        product.setPrice(10);
        product.setStock(5);
        product.setActive(true);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.create(product);

        verify(productValidator).validate(product);
        verify(productRepository).save(product);

        assertSame(product, result);
        assertEquals("Milk", result.getName());
    }

    @Test
    void updateProduct() {
        Long id = 2L;

        Product existing = new Product();
        existing.setId(id);
        existing.setName("Old");
        existing.setPrice(1);
        existing.setStock(1);
        existing.setActive(false);

        Product updated = new Product();
        updated.setName("New");
        updated.setPrice(99);
        updated.setStock(10);
        updated.setActive(true);

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = productService.update(id, updated);
        
        verify(productValidator).validate(existing);
        verify(productRepository).save(existing);

        assertEquals("New", result.getName());
        assertEquals(99, result.getPrice());
        assertEquals(10, result.getStock());
        assertTrue(result.isActive());
        assertSame(existing, result);
    }

    @Test
    void deleteProduct() {
        Long id = 5L;

        Product existing = new Product();
        existing.setId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));

        productService.delete(id);

        verify(productRepository).delete(existing);
    }
}
