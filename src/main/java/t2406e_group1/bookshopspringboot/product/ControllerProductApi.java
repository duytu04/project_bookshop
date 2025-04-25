package t2406e_group1.bookshopspringboot.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import t2406e_group1.bookshopspringboot.product.image.EntityImage;
import t2406e_group1.bookshopspringboot.product.image.ServiceImage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000") // Đảm bảo frontend có thể gửi yêu cầu đến backend
@RestController
@RequestMapping("/api/products")

public class ControllerProductApi {

    @Autowired
    private ServiceProduct serviceProduct;
    
    @Autowired
    private ServiceImage serviceImage;

    // LẤY THÔNG TIN TẤT CẢ SẢN PHẨM
    @GetMapping
    public List<EntityProduct> getAllProduct() {
        return serviceProduct.findAll();
    }

    // LẤY THÔNG TIN SẢN PHẨM THEO ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityProduct> getProductById(@PathVariable int id) {
        Optional<EntityProduct> entityProduct = serviceProduct.findById(id);
        return entityProduct.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    // THÊM MỚI SẢN PHẨM
    @PostMapping
    public ResponseEntity<EntityProduct> createProduct(//@RequestBody EntityProduct entityProduct//
    @RequestParam("name") String name,
    @RequestParam("price") Float price,
    @RequestParam("quantity") Integer quantity,
    @RequestParam("dateAdded") String dateAdded,  // Đảm bảo kiểu dữ liệu date được chuyển đúng
    @RequestParam("author") String author,
    @RequestParam("description") String description,
    @RequestParam("content") String content,
    @RequestParam("language") String language,
    @RequestParam("category") String category,
    @RequestParam("status") Boolean status,
    //@RequestParam("imagePath") String imagePath, // Nhận đường dẫn hình ảnh
    @RequestParam("images") List<MultipartFile> images) throws IOException {

        // Tạo đối tượng sản phẩm mới
        EntityProduct entityProduct = new EntityProduct();
        entityProduct.setName(name);
        entityProduct.setPrice(price);
        entityProduct.setQuantity(quantity); // Hoặc giá trị mặc định khác
        entityProduct.setDateAdded(Date.valueOf(dateAdded));  // Chuyển đổi từ String thành Date
        entityProduct.setAuthor(author);
        entityProduct.setDescription(description);
        entityProduct.setContent(content);
        entityProduct.setLanguage(language);
        entityProduct.setCategory(category);
        entityProduct.setStatus(status);; 
         
        // Lưu sản phẩm vào cơ sở dữ liệu
        EntityProduct savedProduct = serviceProduct.saveEntityProduct(entityProduct);
        
         // Kiểm tra xem có hình ảnh không
    if (images != null && !images.isEmpty()) {
        // Lưu hình ảnh liên kết với sản phẩm
        for (MultipartFile image : images) {
            EntityImage entityImage = new EntityImage();
            entityImage.setImagePath(image.getOriginalFilename());
            //entityImage.setImagePath(imagePath);  // Lưu đường dẫn tệp
             // Liên kết với sản phẩm đã lưu
            entityImage.setProduct(savedProduct); 
            serviceImage.saveEntityImage(entityImage);
            // Lưu ảnh vào thư mục hoặc cơ sở dữ liệu, tùy thuộc vào yêu cầu của bạn
        }
    }

        return ResponseEntity.status(201).body(savedProduct);  // Chuyển thành mã trạng thái 201 khi thêm thành công
        // return serviceProduct.saveEntityProduct(entityProduct);
    }

    // SỬA SẢN PHẨM THEO ID
    @PutMapping("/{id}")
    public ResponseEntity<EntityProduct> updateProduct(@PathVariable int id, @RequestBody EntityProduct productDetails) {
        Optional<EntityProduct> optionalEntityProduct = serviceProduct.findById(id);
        if (optionalEntityProduct.isPresent()) {
            EntityProduct entityProduct = optionalEntityProduct.get();
            entityProduct.setName(productDetails.getName());
            entityProduct.setPrice(productDetails.getPrice());
            entityProduct.setQuantity(productDetails.getQuantity());
            entityProduct.setDateAdded(productDetails.getDateAdded());
            entityProduct.setAuthor(productDetails.getAuthor());
            entityProduct.setDescription(productDetails.getDescription());
            entityProduct.setContent(productDetails.getContent());
            entityProduct.setLanguage(productDetails.getLanguage());
            entityProduct.setCategory(productDetails.getCategory());
            entityProduct.setStatus(productDetails.getStatus());
            return ResponseEntity.ok(serviceProduct.saveEntityProduct(entityProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // XÓA SẢN PHẨM THEO ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        if (serviceProduct.existsById(id)) {
            serviceProduct.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // LẤY THÔNG TIN TẤT CẢ ẢNH
    @GetMapping("/images")
    public List<EntityImage> getAllImages() {
        return serviceImage.findAll();
    }

    // LẤY THÔNG TIN ẢNH THEO ID
    @GetMapping("/{id}/images")
    public ResponseEntity<EntityImage> getProductImageById(@PathVariable int id) {
        Optional<EntityImage> entityImage = serviceImage.findById(id);
        return entityImage.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    // THÊM MỚI ẢNH
    @PostMapping("/images")
    public EntityImage createProductImage(@RequestBody EntityImage entityImage) {
        return serviceImage.saveEntityImage(entityImage);
    }

    // SỬA ẢNH THEO ID
    @PutMapping("/{id}/images")
    public ResponseEntity<EntityImage> updateProductImage(@PathVariable int id, @RequestBody EntityImage productDetails) {
        Optional<EntityImage> optionalEntityImage = serviceImage.findById(id);
        if (optionalEntityImage.isPresent()) {
            EntityImage entityImage = optionalEntityImage.get();
            entityImage.setImagePath(productDetails.getImagePath());
            return ResponseEntity.ok(serviceImage.saveEntityImage(entityImage));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // XÓA ẢNH THEO ID
    @DeleteMapping("/{id}/images")
    public ResponseEntity<Void> deleteProductImage(@PathVariable int id) {
        if (serviceImage.existsById(id)) {
            serviceImage.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


