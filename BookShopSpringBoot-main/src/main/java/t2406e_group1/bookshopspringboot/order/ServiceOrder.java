package t2406e_group1.bookshopspringboot.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceOrder {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> findByUserId(int userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> findById(int id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO);
    }

    public OrderDTO saveEntityOrder(EntityOrder entityOrder) {
        if (entityOrder.getOrderItems() == null || entityOrder.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được rỗng");
        }
        if (entityOrder.getUser() == null) {
            throw new IllegalArgumentException("Người dùng không được rỗng");
        }

        entityOrder.getOrderItems().forEach(item -> {
            if (item.getQuantity() <= 0 || item.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Số lượng và giá phải lớn hơn 0");
            }
            item.setOrder(entityOrder);
        });

        entityOrder.setTotal(entityOrder.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        EntityOrder savedOrder = orderRepository.save(entityOrder);
        return convertToDTO(savedOrder);
    }

    public void deleteById(int id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy đơn hàng với id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(EntityOrder order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setUserName(order.getUser().getName());
        orderDTO.setUserEmail(order.getUser().getEmail());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setPhoneNumber(order.getPhoneNumber());
        orderDTO.setOrderItems(order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProductId());
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setItemTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemDTO;
        }).collect(Collectors.toList()));
        return orderDTO;
    }
}