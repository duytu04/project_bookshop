// package t2406e_group1.bookshopspringboot.supplier;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/supplier")
// public class ControllerSupplier {

//     @Autowired
//     private ServiceSupplier supplierService;

//     // Lấy danh sách tất cả nhà cung cấp
//     @GetMapping
//     public ResponseEntity<List<EntitySupplier>> getAllSuppliers() {
//         List<EntitySupplier> suppliers = supplierService.getAllSuppliers();
//         return ResponseEntity.ok(suppliers);
//     }

//     // Lấy nhà cung cấp theo ID
//     @GetMapping("/{id}")
//     public ResponseEntity<EntitySupplier> getSupplierById(@PathVariable int id) {
//         Optional<EntitySupplier> supplier = supplierService.getSupplierById(id);
//         return supplier.map(ResponseEntity::ok)
//                 .orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // Thêm nhà cung cấp mới
//     @PostMapping
//     public ResponseEntity<EntitySupplier> createSupplier(@RequestBody EntitySupplier supplier) {
//         EntitySupplier newSupplier = supplierService.saveSupplier(supplier);
//         return ResponseEntity.ok(newSupplier);
//     }

//     // Cập nhật thông tin nhà cung cấp
//     // @PutMapping("/{id}")
//     // public ResponseEntity<EntitySupplier> updateSupplier(@PathVariable int id, @RequestBody EntitySupplier supplier) {
//     //     Optional<EntitySupplier> existingSupplier = supplierService.getSupplierById(id);
//     //     if (existingSupplier.isPresent()) {
//     //         supplier.setId(id);
//     //         EntitySupplier updatedSupplier = supplierService.saveSupplier(supplier);
//     //         return ResponseEntity.ok(updatedSupplier);
//     //     } else {
//     //         return ResponseEntity.notFound().build();
//     //     }
//     // }
//     @PutMapping("/{id}")
// public ResponseEntity<?> updateSupplier(@PathVariable Integer id, @RequestBody EntitySupplier EntitySupplier) {
//     try {
//         EntitySupplier updatedSupplier = supplierService.updateSupplier(id, EntitySupplier);
//         return ResponseEntity.ok(updatedSupplier);
//     } catch (Exception e) {
//         return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                 .body(new ErrorResponse("Không thể cập nhật nhà cung cấp: " + e.getMessage()));
//     }
// }

//     // Xóa nhà cung cấp
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
//         Optional<EntitySupplier> existingSupplier = supplierService.getSupplierById(id);
//         if (existingSupplier.isPresent()) {
//             supplierService.deleteSupplier(id);
//             return ResponseEntity.noContent().build();
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }



package t2406e_group1.bookshopspringboot.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Thêm nhà cung cấp
    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody EntitySupplier supplier) {
        try {
            EntitySupplier createdSupplier = supplierService.createSupplier(supplier);
            return ResponseEntity.ok(createdSupplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Có lỗi khi thêm nhà cung cấp: " + e.getMessage()));
        }
    }

    // Lấy danh sách tất cả nhà cung cấp
    @GetMapping
    public ResponseEntity<List<EntitySupplier>> getAllSuppliers() {
        List<EntitySupplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    // Lấy nhà cung cấp theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable Integer id) {
        Optional<EntitySupplier> supplier = supplierService.getSupplierById(id);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplier.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Không tìm thấy nhà cung cấp với ID: " + id));
        }
    }

    // Cập nhật nhà cung cấp
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Integer id, @RequestBody EntitySupplier supplierDetails) {
        try {
            EntitySupplier updatedSupplier = supplierService.updateSupplier(id, supplierDetails);
            return ResponseEntity.ok(updatedSupplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Không thể cập nhật nhà cung cấp: " + e.getMessage()));
        }
    }

    // Xóa nhà cung cấp
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Integer id) {
        try {
            supplierService.deleteSupplier(id);
            return ResponseEntity.ok(new ErrorResponse("Xóa nhà cung cấp thành công."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Không tìm thấy nhà cung cấp với ID: " + id));
        }
    }
}

// Lớp trả về thông báo
class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}