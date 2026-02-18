package se.iths.stefan.labbdrift.validator;

import org.springframework.stereotype.Component;
import se.iths.stefan.labbdrift.exception.product.InvalidProductNameException;
import se.iths.stefan.labbdrift.exception.product.InvalidProductPriceException;
import se.iths.stefan.labbdrift.exception.product.InvalidProductStockException;
import se.iths.stefan.labbdrift.model.Product;

@Component
public class ProductValidator {

    public void validate(Product p) {
        if (p == null) {
            throw new IllegalArgumentException("Product får inte vara null");
        }

        if (p.getName() == null || p.getName().trim().isEmpty()) {
            throw new InvalidProductNameException("Produktnamn får inte vara tomt");
        }

        if (p.getPrice() <= 0) {
            throw new InvalidProductPriceException("Pris måste vara större än 0");
        }

        if (p.getStock() < 0) {
            throw new InvalidProductStockException("Lagersaldo får inte vara negativt");
        }
    }
}
