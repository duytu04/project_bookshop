// package t2406e_group1.bookshopspringboot.product.image;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/test3/image")
// public class ControllerImageApi {

//     @Autowired
//     private ServiceImage serviceImage;

//     // LẤY THÔNG TIN TẤT CẢ ẢNH
//     @GetMapping
//     public List<EntityImage> getAllImages() {
//         return serviceImage.findAll();
//     }

//     // LẤY THÔNG TIN ẢNH THEO ID
//     @GetMapping("/{id}")
//     public ResponseEntity<EntityImage> getProductById(@PathVariable int id) {
//         Optional<EntityImage> entityImage = serviceImage.findById(id);
//         return entityImage.map(ResponseEntity::ok).orElseGet(() ->
//                 ResponseEntity.notFound().build());
//     }

//     // THÊM MỚI ẢNH
//     @PostMapping
//     public EntityImage createProduct(@RequestBody EntityImage entityImage) {
//         return serviceImage.saveEntityImage(entityImage);
//     }

//     // SỬA ẢNH THEO ID
//     @PutMapping("/{id}")
//     public ResponseEntity<EntityImage> updateProduct(@PathVariable int id, @RequestBody EntityImage productDetails) {
//         Optional<EntityImage> optionalEntityImage = serviceImage.findById(id);
//         if (optionalEntityImage.isPresent()) {
//             EntityImage entityImage = optionalEntityImage.get();
//             entityImage.setImagePath(productDetails.getImagePath());
//             return ResponseEntity.ok(serviceImage.saveEntityImage(entityImage));
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // XÓA ẢNH THEO ID
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteImage(@PathVariable int id) {
//         if (serviceImage.existsById(id)) {
//             serviceImage.deleteById(id);
//             return ResponseEntity.noContent().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }