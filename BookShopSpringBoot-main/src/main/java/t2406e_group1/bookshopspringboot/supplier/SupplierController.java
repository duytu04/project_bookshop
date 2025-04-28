

// package t2406e_group1.bookshopspringboot.supplier;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/supplier")
// public class SupplierController {

//     @Autowired
//     private SupplierService supplierService;

//     // Thêm nhà cung cấp
//     @PostMapping
//     public ResponseEntity<?> addSupplier(@RequestBody EntitySupplier supplier) {
//         try {
//             EntitySupplier createdSupplier = supplierService.createSupplier(supplier);
//             return ResponseEntity.ok(createdSupplier);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                     .body(new ErrorResponse("Có lỗi khi thêm nhà cung cấp: " + e.getMessage()));
//         }
//     }

//     // Lấy danh sách tất cả nhà cung cấp
//     @GetMapping
//     public ResponseEntity<List<EntitySupplier>> getAllSuppliers() {
//         List<EntitySupplier> suppliers = supplierService.getAllSuppliers();
//         return ResponseEntity.ok(suppliers);
//     }

//     // Lấy nhà cung cấp theo ID
//     @GetMapping("/{id}")
//     public ResponseEntity<?> getSupplierById(@PathVariable Integer id) {
//         Optional<EntitySupplier> supplier = supplierService.getSupplierById(id);
//         if (supplier.isPresent()) {
//             return ResponseEntity.ok(supplier.get());
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body(new ErrorResponse("Không tìm thấy nhà cung cấp với ID: " + id));
//         }
//     }

//     // Cập nhật nhà cung cấp
//     @PutMapping("/{id}")
//     public ResponseEntity<?> updateSupplier(@PathVariable Integer id, @RequestBody EntitySupplier supplierDetails) {
//         try {
//             EntitySupplier updatedSupplier = supplierService.updateSupplier(id, supplierDetails);
//             return ResponseEntity.ok(updatedSupplier);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                     .body(new ErrorResponse("Không thể cập nhật nhà cung cấp: " + e.getMessage()));
//         }
//     }

//     // Xóa nhà cung cấp
//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> deleteSupplier(@PathVariable Integer id) {
//         try {
//             supplierService.deleteSupplier(id);
//             return ResponseEntity.ok(new ErrorResponse("Xóa nhà cung cấp thành công."));
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body(new ErrorResponse("Không tìm thấy nhà cung cấp với ID: " + id));
//         }
//     }
// }

// // Lớp trả về thông báo
// class ErrorResponse {
//     private String message;

//     public ErrorResponse(String message) {
//         this.message = message;
//     }

//     public String getMessage() {
//         return message;
//     }

//     public void setMessage(String message) {
//         this.message = message;
//     }
// }



package t2406e_group1.bookshopspringboot.supplier;

// Import các thư viện cần thiết
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Dùng để ghi log
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Dùng để trả phản hồi HTTP
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException; // Dùng để xử lý lỗi và ném mã HTTP phù hợp

import java.util.List;
import java.util.Optional;

// Đây là controller REST cho quản lý nhà cung cấp
@RestController
@RequestMapping("/api/supplier") // Tất cả các endpoint sẽ có tiền tố là /api/supplier
public class SupplierController {

    // Khởi tạo logger để ghi log ra console
    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    // Inject (tiêm) SupplierService để xử lý logic nghiệp vụ
    @Autowired
    private SupplierService supplierService;

    // -----------------------------
    // TẠO MỚI NHÀ CUNG CẤP (POST)
    // -----------------------------
    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody EntitySupplier supplier) {
        logger.info("Nhận yêu cầu POST để thêm nhà cung cấp: {}", supplier.getName());
        try {
            // Gọi service để tạo nhà cung cấp mới
            EntitySupplier createdSupplier = supplierService.createSupplier(supplier);
            // Trả về phản hồi HTTP 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
        } catch (Exception e) {
            logger.error("Lỗi khi thêm nhà cung cấp: {}", e.getMessage());
            // Nếu có lỗi, trả về mã 400 với thông báo lỗi
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi khi thêm nhà cung cấp", e);
        }
    }

    // ---------------------------------------
    // LẤY DANH SÁCH TẤT CẢ NHÀ CUNG CẤP (GET)
    // ---------------------------------------
    @GetMapping
    public ResponseEntity<List<EntitySupplier>> getAllSuppliers() {
        logger.info("Nhận yêu cầu GET để lấy danh sách nhà cung cấp");
        // Lấy tất cả nhà cung cấp từ service
        List<EntitySupplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers); // Trả về mã 200 với danh sách
    }

    // --------------------------------------------
    // LẤY NHÀ CUNG CẤP THEO ID CỤ THỂ (GET /{id})
    // --------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<EntitySupplier> getSupplierById(@PathVariable Integer id) {
        logger.info("Nhận yêu cầu GET nhà cung cấp với id: {}", id);
        Optional<EntitySupplier> supplier = supplierService.getSupplierById(id);
        if (supplier.isPresent()) {
            return ResponseEntity.ok(supplier.get()); // Trả về nếu tồn tại
        } else {
            logger.warn("Không tìm thấy nhà cung cấp với id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhà cung cấp với ID: " + id);
        }
    }

    // ---------------------------------------
    // CẬP NHẬT NHÀ CUNG CẤP THEO ID (PUT /{id})
    // ---------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<EntitySupplier> updateSupplier(@PathVariable Integer id, @RequestBody EntitySupplier supplierDetails) {
        logger.info("Nhận yêu cầu PUT để cập nhật nhà cung cấp với id: {}", id);
        try {
            // Gọi service để cập nhật thông tin
            EntitySupplier updatedSupplier = supplierService.updateSupplier(id, supplierDetails);
            return ResponseEntity.ok(updatedSupplier); // Trả về nhà cung cấp đã cập nhật
        } catch (ResponseStatusException e) {
            logger.error("Lỗi khi cập nhật nhà cung cấp id {}: {}", id, e.getMessage());
            throw e; // Ném lỗi nếu là ResponseStatusException
        } catch (Exception e) {
            logger.error("Lỗi không xác định khi cập nhật id {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể cập nhật nhà cung cấp", e);
        }
    }

    // -----------------------------------
    // XÓA NHÀ CUNG CẤP THEO ID (DELETE)
    // -----------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        logger.info("Nhận yêu cầu DELETE nhà cung cấp với id: {}", id);
        try {
            supplierService.deleteSupplier(id); // Gọi service để xóa
            return ResponseEntity.noContent().build(); // Trả về 204 No Content nếu thành công
        } catch (ResponseStatusException e) {
            logger.error("Lỗi khi xóa nhà cung cấp id {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Lỗi không xác định khi xóa nhà cung cấp id {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa nhà cung cấp", e);
        }
    }
}
