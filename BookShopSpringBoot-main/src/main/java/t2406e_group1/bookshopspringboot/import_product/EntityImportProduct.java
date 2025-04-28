

package t2406e_group1.bookshopspringboot.import_product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

import t2406e_group1.bookshopspringboot.product.EntityProduct;
import t2406e_group1.bookshopspringboot.supplier.EntitySupplier;

@Entity
@Table(name = "entity_import_product")
@Getter
@Setter
public class EntityImportProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private EntityProduct product;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private EntitySupplier supplier;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date importDate;
}