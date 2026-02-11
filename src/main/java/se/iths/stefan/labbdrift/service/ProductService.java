package se.iths.stefan.labbdrift.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.labbdrift.exception.ResourcesNotFoundException;
import se.iths.stefan.labbdrift.model.Product;
import se.iths.stefan.labbdrift.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product getOne(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Product with id " + id + " not found"));
    }

    public Product create(Product product) {
        return repo.save(product);
    }

    public Product update(Long id, Product updated) {
        Product existing = getOne(id);

        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setActive(updated.isActive());

        return repo.save(existing);
    }

    public void delete(Long id) {
        Product existing = getOne(id);
        repo.delete(existing);
    }
}
