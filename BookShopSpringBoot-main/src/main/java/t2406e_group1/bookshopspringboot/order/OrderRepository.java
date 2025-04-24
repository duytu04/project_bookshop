package t2406e_group1.bookshopspringboot.order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<EntityOrder, Integer> {
    List<EntityOrder> findByUserId(int userId);
}