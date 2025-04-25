package t2406e_group1.bookshopspringboot.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<EntityOrderItem, Integer> {
}