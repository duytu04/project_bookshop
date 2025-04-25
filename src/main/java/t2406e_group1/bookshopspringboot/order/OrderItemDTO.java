package t2406e_group1.bookshopspringboot.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO {
    private int id;
    private int productId;
    private String name;
    private int quantity;
    private BigDecimal price;
    private BigDecimal itemTotal; // quantity * price
}