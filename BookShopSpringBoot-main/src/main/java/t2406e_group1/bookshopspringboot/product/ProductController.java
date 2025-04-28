// package t2406e_group1.bookshopspringboot.product;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/product")
// public class ControllerProductApi {

//     @Autowired
//     private ServiceProduct serviceProduct;

//     // LẤY THÔNG TIN TẤT CẢ SẢN PHẨM
//     @GetMapping
//     public List<EntityProduct> getAllProduct() {
//         return serviceProduct.findAll();
//     }

//     // LẤY THÔNG TIN SẢN PHẨM THEO ID
//     @GetMapping("/{id}")
//     public ResponseEntity<EntityProduct> getProductById(@PathVariable int id) {
//         Optional<EntityProduct> entityProduct = serviceProduct.findById(id);
//         return entityProduct.map(ResponseEntity::ok).orElseGet(() ->
//                 ResponseEntity.notFound().build());
//     }

//     // THÊM MỚI SẢN PHẨM
//     @PostMapping
//     public EntityProduct createProduct(@RequestBody EntityProduct entityProduct) {
//         return serviceProduct.saveEntityProduct(entityProduct);
//     }

//     // SỬA SẢN PHẨM THEO ID
//     @PutMapping("/{id}")
//     public ResponseEntity<EntityProduct> updateProduct(@PathVariable int id, @RequestBody EntityProduct productDetails) {
//         Optional<EntityProduct> optionalEntityProduct = serviceProduct.findById(id);
//         if (optionalEntityProduct.isPresent()) {
//             EntityProduct entityProduct = optionalEntityProduct.get();
//             entityProduct.setName(productDetails.getName());
//             entityProduct.setPrice(productDetails.getPrice());
//             entityProduct.setQuantity(productDetails.getQuantity());
//             return ResponseEntity.ok(serviceProduct.saveEntityProduct(entityProduct));
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // XÓA SẢN PHẨM THEO ID
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
//         if (serviceProduct.existsById(id)) {
//             serviceProduct.deleteById(id);
//             return ResponseEntity.noContent().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }



package t2406e_group1.bookshopspringboot.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ServiceProduct productService;

    @GetMapping
    public ResponseEntity<List<EntityProduct>> getAllProducts() {
        logger.info("Received GET request for all products");
        List<EntityProduct> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityProduct> getProductById(@PathVariable Integer id) {
        logger.info("Received GET request for product with id: {}", id);
        Optional<EntityProduct> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            logger.warn("Product with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm với ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<EntityProduct> addProduct(@RequestBody EntityProduct product) {
        logger.info("Received POST request to add product: {}", product.getName());
        try {
            EntityProduct createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi khi thêm sản phẩm", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityProduct> updateProduct(@PathVariable Integer id, @RequestBody EntityProduct productDetails) {
        logger.info("Received PUT request to update product with id: {}", id);
        try {
            EntityProduct updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (ResponseStatusException e) {
            logger.error("Error updating product id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error updating product id {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể cập nhật sản phẩm", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        logger.info("Received DELETE request for product with id: {}", id);
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            logger.error("Error deleting product id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error deleting product id {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa sản phẩm", e);
        }
    }
}
