// package t2406e_group1.bookshopspringboot.product;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// // Sử dụng constructor injection thay vì field injection:
// public class ServiceProduct {
//     private final JpaProduct jpaProduct;
    
//     @Autowired
//     public ServiceProduct(JpaProduct jpaProduct) {
//         this.jpaProduct = jpaProduct;
//     }

//     public List<EntityProduct> findAll() {
//         return jpaProduct.findAll();
//     }

//     public Optional<EntityProduct> findById(int id) {
//         return jpaProduct.findById(id);
//     }

//     public EntityProduct saveEntityProduct(EntityProduct entityProduct) {
//         return jpaProduct.save(entityProduct);
//     }

//     public boolean existsById(int id) {
//         return jpaProduct.existsById(id);
//     }

//     public void deleteById(int id) {
//         jpaProduct.deleteById(id);
//     }
// }


package t2406e_group1.bookshopspringboot.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProduct {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProduct.class);

    @Autowired
    private ProductRepository productRepository; // Correct type: ProductRepository, not ServiceProduct

    public List<EntityProduct> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    public Optional<EntityProduct> getProductById(Integer id) {
        logger.info("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    public EntityProduct createProduct(EntityProduct product) {
        logger.info("Creating new product: {}", product.getName());
        return productRepository.save(product);
    }

    public EntityProduct updateProduct(Integer id, EntityProduct productDetails) {
        logger.info("Updating product with id: {}", id);
        Optional<EntityProduct> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            logger.warn("Product with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm với ID: " + id);
        }

        EntityProduct product = existingProduct.get();
        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        logger.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            logger.warn("Product with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
    }
}