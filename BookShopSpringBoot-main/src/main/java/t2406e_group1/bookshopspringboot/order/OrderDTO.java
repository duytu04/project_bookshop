package t2406e_group1.bookshopspringboot.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private int id;
    private int userId;
    private String userName;
    private String userEmail;
    private Date orderDate;
    private BigDecimal total;
    private String status;
    private String phoneNumber;
    private List<OrderItemDTO> orderItems;
}