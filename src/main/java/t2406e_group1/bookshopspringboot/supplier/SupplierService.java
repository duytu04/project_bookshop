

// package t2406e_group1.bookshopspringboot.supplier;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class SupplierService {

//     @Autowired
//     private SupplierRepository supplierRepository;

//     // Thêm nhà cung cấp
//     public EntitySupplier createSupplier(EntitySupplier supplier) {
//         return supplierRepository.save(supplier);
//     }

//     // Lấy danh sách tất cả nhà cung cấp
//     public List<EntitySupplier> getAllSuppliers() {
//         return supplierRepository.findAll();
//     }

//     // Lấy nhà cung cấp theo ID
//     public Optional<EntitySupplier> getSupplierById(Integer id) {
//         return supplierRepository.findById(id);
//     }

//     // Cập nhật nhà cung cấp
//     public EntitySupplier updateSupplier(Integer id, EntitySupplier supplierDetails) {
//         Optional<EntitySupplier> existingSupplier = supplierRepository.findById(id);
//         if (existingSupplier.isPresent()) {
//             EntitySupplier supplier = existingSupplier.get();
//             supplier.setName(supplierDetails.getName());
//             supplier.setAddress(supplierDetails.getAddress());
//             supplier.setPhoneNumber(supplierDetails.getPhoneNumber());
//             supplier.setEmail(supplierDetails.getEmail());
//             return supplierRepository.save(supplier);
//         } else {
//             throw new RuntimeException("Không tìm thấy nhà cung cấp với ID: " + id);
//         }
//     }

//     // Xóa nhà cung cấp
//     public void deleteSupplier(Integer id) {
//         if (supplierRepository.existsById(id)) {
//             supplierRepository.deleteById(id);
//         } else {
//             throw new RuntimeException("Không tìm thấy nhà cung cấp với ID: " + id);
//         }
//     }
// }


package t2406e_group1.bookshopspringboot.supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import t2406e_group1.bookshopspringboot.import_product.JpaImportProduct;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    
    private JpaImportProduct importProductRepository ; // Thay thế bằng tên đúng của repository

    // Hàm kiểm tra số điện thoại hợp lệ
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+\\d{1,3}\\d{10}$");
    }

    // Tạo nhà cung cấp mới
    public EntitySupplier createSupplier(EntitySupplier supplier) {
        logger.info("Creating new supplier: {}", supplier);

        if (!isValidPhoneNumber(supplier.getPhoneNumber())) {
            logger.warn("Invalid phone number: {}", supplier.getPhoneNumber());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại không hợp lệ! Định dạng: +mã quốc gia + 10 chữ số.");
        }

        return supplierRepository.save(supplier);
    }

    // Lấy danh sách tất cả nhà cung cấp
    public List<EntitySupplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // Lấy nhà cung cấp theo ID
    public Optional<EntitySupplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    // Cập nhật thông tin nhà cung cấp
    public EntitySupplier updateSupplier(Integer id, EntitySupplier supplierDetails) {
        Optional<EntitySupplier> existingSupplier = supplierRepository.findById(id);

        if (existingSupplier.isEmpty()) {
            logger.warn("Supplier with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy nhà cung cấp với ID: " + id);
        }

        if (!isValidPhoneNumber(supplierDetails.getPhoneNumber())) {
            logger.warn("Invalid phone number: {}", supplierDetails.getPhoneNumber());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại không hợp lệ! Định dạng: +mã quốc gia + 10 chữ số.");
        }

        EntitySupplier supplier = existingSupplier.get();
        supplier.setName(supplierDetails.getName());
        supplier.setAddress(supplierDetails.getAddress());
        supplier.setPhoneNumber(supplierDetails.getPhoneNumber());
        supplier.setEmail(supplierDetails.getEmail());

        logger.info("Updating supplier with id {}", id);
        return supplierRepository.save(supplier);
    }

    // Xóa nhà cung cấp (có kiểm tra liên kết với import_product)
    public void deleteSupplier(Integer id) {
        logger.info("Attempting to delete supplier with id: {}", id);

        if (!supplierRepository.existsById(id)) {
            logger.warn("Supplier with id {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found");
        }

        long count = importProductRepository.countBySupplierId(id);
        if (count > 0) {
            logger.warn("Cannot delete supplier id {} because it is referenced by {} import product(s)", id, count);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Không thể xóa nhà cung cấp vì đang được sử dụng trong " + count + " phiếu nhập.");
        }

        supplierRepository.deleteById(id);
        logger.info("Supplier with id {} deleted successfully", id);
    }
}
