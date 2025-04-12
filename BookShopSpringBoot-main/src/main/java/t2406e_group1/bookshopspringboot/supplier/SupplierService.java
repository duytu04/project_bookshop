// package t2406e_group1.bookshopspringboot.supplier;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class ServiceSupplier {

//     @Autowired
//     private JpaSupplier supplierRepository;

//     // Hàm kiểm tra số điện thoại hợp lệ (bắt buộc mã quốc gia và đúng 10 chữ số)
//     public boolean isValidPhoneNumber(String phoneNumber) {
//         return phoneNumber.matches("^\\+\\d{1,3}\\d{10}$");  
//     }

//     // Lấy danh sách tất cả các nhà cung cấp
//     public List<EntitySupplier> getAllSuppliers() {
//         return supplierRepository.findAll();
//     }

//     // Lấy nhà cung cấp theo ID
//     public Optional<EntitySupplier> getSupplierById(int id) {
//         return supplierRepository.findById(id);
//     }

   

//     // Thêm hoặc cập nhật nhà cung cấp (có kiểm tra số điện thoại hợp lệ)
//     public EntitySupplier saveSupplier(EntitySupplier supplier) {
//         if (!isValidPhoneNumber(supplier.getPhoneNumber())) {
//             throw new IllegalArgumentException("Số điện thoại không hợp lệ! Phải có mã quốc gia (+84, +1, ...) và đúng 10 chữ số.");
//         }
//         return supplierRepository.save(supplier);
//     }

//     // Xóa nhà cung cấp theo ID
//     public void deleteSupplier(int id) {
//         supplierRepository.deleteById(id);
//     }
// }


package t2406e_group1.bookshopspringboot.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // Thêm nhà cung cấp
    public EntitySupplier createSupplier(EntitySupplier supplier) {
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

    // Cập nhật nhà cung cấp
    public EntitySupplier updateSupplier(Integer id, EntitySupplier supplierDetails) {
        Optional<EntitySupplier> existingSupplier = supplierRepository.findById(id);
        if (existingSupplier.isPresent()) {
            EntitySupplier supplier = existingSupplier.get();
            supplier.setName(supplierDetails.getName());
            supplier.setAddress(supplierDetails.getAddress());
            supplier.setPhoneNumber(supplierDetails.getPhoneNumber());
            supplier.setEmail(supplierDetails.getEmail());
            return supplierRepository.save(supplier);
        } else {
            throw new RuntimeException("Không tìm thấy nhà cung cấp với ID: " + id);
        }
    }

    // Xóa nhà cung cấp
    public void deleteSupplier(Integer id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy nhà cung cấp với ID: " + id);
        }
    }
}