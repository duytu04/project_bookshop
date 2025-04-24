package t2406e_group1.bookshopspringboot.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "entity_order_items")
@Getter
@Setter
public class EntityOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private EntityOrder order;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    public EntityOrderItem() {
        this.quantity = 1;
        this.price = BigDecimal.ZERO;
    }
}