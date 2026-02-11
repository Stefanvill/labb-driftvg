package se.iths.stefan.labbdrift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.stefan.labbdrift.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
