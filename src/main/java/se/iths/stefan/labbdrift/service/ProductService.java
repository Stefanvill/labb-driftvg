package se.iths.stefan.labbdrift.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.labbdrift.exception.product.ProductNotFoundException;
import se.iths.stefan.labbdrift.model.Product;
import se.iths.stefan.labbdrift.repository.ProductRepository;
import se.iths.stefan.labbdrift.validator.ProductValidator;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository repo, ProductValidator productValidator) {
        this.repo = repo;
        this.productValidator = productValidator;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getOne(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    public Product create(Product product) {
        productValidator.validate(product);
        return repo.save(product);
    }

    public Product update(Long id, Product updated) {
        Product existing = getOne(id);

        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setActive(updated.isActive());

        productValidator.validate(existing);
        return repo.save(existing);
    }


    public void delete(Long id) {
        Product existing = getOne(id);
        repo.delete(existing);
    }
}
