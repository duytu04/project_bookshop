// package t2406e_group1.bookshopspringboot.order;

// import jakarta.persistence.*;
// import lombok.Getter;
// import lombok.Setter;
// import t2406e_group1.bookshopspringboot.user.EntityUser;
// import java.math.BigDecimal;
// import java.util.Date;
// import java.util.List;

// @Entity
// @Table(name = "orders")
// @Getter
// @Setter
// public class EntityOrder {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "order_id")
//     private int id;

//     @ManyToOne
//     @JoinColumn(name = "user_id", nullable = false)
//     private EntityUser user;

//     @Temporal(TemporalType.TIMESTAMP)
//     @Column(name = "order_date", nullable = false)
//     private Date orderDate;

//     @Column(nullable = false)
//     private BigDecimal total;

//     @Column(nullable = false, length = 50)
//     private String status;

//     @Column(name = "phone_number", nullable = false)
//     private String phoneNumber;

//     @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//     private List<EntityOrderItem> orderItems;

//     public EntityOrder() {
//         this.orderDate = new Date();
//         this.status = "Pending";
//         this.total = BigDecimal.ZERO;
//     }
// }

package t2406e_group1.bookshopspringboot.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import t2406e_group1.bookshopspringboot.user.EntityUser;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entity_orders")
@Getter
@Setter
public class EntityOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private EntityUser user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EntityOrderItem> orderItems;

    public EntityOrder() {
        this.orderDate = new Date();
        this.status = "Pending";
        this.total = BigDecimal.ZERO;
    }
}