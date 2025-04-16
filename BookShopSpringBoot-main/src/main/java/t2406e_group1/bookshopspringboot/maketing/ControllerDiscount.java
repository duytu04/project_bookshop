package t2406e_group1.bookshopspringboot.maketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import t2406e_group1.bookshopspringboot.product.EntityProduct;
import t2406e_group1.bookshopspringboot.product.ServiceProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/discounts") // Endpoint chính của API
public class ControllerDiscount {

    @Autowired
    private ServiceDiscount serviceDiscount;
    @Autowired
    private ServiceProduct productService;

    //  LẤY DANH SÁCH TẤT CẢ KHUYẾN MÃI
    @GetMapping
    public ResponseEntity<List<EntityDiscount>> getAllDiscounts() {
        List<EntityDiscount> discounts = serviceDiscount.findAll();
        return ResponseEntity.ok(discounts);
    }

    //  LẤY THÔNG TIN KHUYẾN MÃI THEO ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityDiscount> getDiscountById(@PathVariable int id) {
        Optional<EntityDiscount> discount = serviceDiscount.findById(id);
        return discount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  LẤY KHUYẾN MÃI THEO GIÁ SALE
    @GetMapping("/saleprice/{salePrice}")
    public ResponseEntity<List<EntityDiscount>> getDiscountsBySalePrice(@PathVariable float salePrice) {
        List<EntityDiscount> discounts = serviceDiscount.findBySalePrice(salePrice);
        if (discounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(discounts);
    }
      
    //  THÊM KHUYẾN MÃI MỚI
    // @PostMapping
    // public ResponseEntity<EntityDiscount> createDiscount(@RequestBody EntityDiscount entityDiscount) {
    //     EntityDiscount savedDiscount = serviceDiscount.saveEntityDiscount(entityDiscount);
    //     return ResponseEntity.ok(savedDiscount);
    // }

    @PostMapping
    public ResponseEntity<EntityDiscount> createDiscount(@RequestBody EntityDiscount entityDiscount) {
        // Kiểm tra xem entityDiscount hoặc product có null không
        if (entityDiscount == null || entityDiscount.getProduct() == null) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu product không hợp lệ
        }
    
        // Kiểm tra id của product
        Integer productId = entityDiscount.getProduct().getId();
        if (productId == null || productId <= 0) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu id không hợp lệ
        }
    
        // Kiểm tra xem sản phẩm có tồn tại không
        Optional<EntityProduct> product = productService.getProductById(productId);
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu product không tồn tại
        }
    
        // Gán product đã tồn tại vào entityDiscount
        entityDiscount.setProduct(product.get());
    
        // Kiểm tra dữ liệu đầu vào
        if (entityDiscount.InputError()) {
            return ResponseEntity.badRequest().body(null); // Trả về lỗi nếu dữ liệu không hợp lệ
        }
    
        // Lưu entityDiscount
        EntityDiscount savedDiscount = serviceDiscount.saveEntityDiscount(entityDiscount);
        return ResponseEntity.ok(savedDiscount);
    }


    //  CẬP NHẬT THÔNG TIN KHUYẾN MÃI
    @PutMapping("/{id}")
    public ResponseEntity<EntityDiscount> updateDiscount(@PathVariable int id, @RequestBody EntityDiscount updatedDiscount) {
        Optional<EntityDiscount> existingDiscount = serviceDiscount.findById(id);
        if (existingDiscount.isPresent()) {
            updatedDiscount.setId(id); // Đảm bảo ID không thay đổi
            EntityDiscount savedDiscount = serviceDiscount.saveEntityDiscount(updatedDiscount);
            return ResponseEntity.ok(savedDiscount);
        }
        return ResponseEntity.notFound().build();
    }

    // XOÁ KHUYẾN MÃI THEO ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int id) {
        if (serviceDiscount.existsById(id)) {
            serviceDiscount.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
