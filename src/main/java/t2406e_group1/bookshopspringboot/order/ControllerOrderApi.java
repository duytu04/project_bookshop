package t2406e_group1.bookshopspringboot.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class ControllerOrderApi {

    @Autowired
    private ServiceOrder serviceOrder;

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(serviceOrder.findAll());
    }

    // Lấy đơn hàng theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(serviceOrder.findByUserId(userId));
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        Optional<OrderDTO> optionalOrder = serviceOrder.findById(id);
        return optionalOrder.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(null)); // Frontend expects null or handles 404
    }

    // Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody EntityOrder entityOrder) {
        OrderDTO orderDTO = serviceOrder.saveEntityOrder(entityOrder);
        return ResponseEntity.ok(orderDTO);
    }

    // Xóa đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        serviceOrder.deleteById(id);
        return ResponseEntity.ok("Đơn hàng đã được xóa!");
    }
}