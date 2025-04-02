package t2406e_group1.bookshopspringboot.maketing; // Khai báo package chứa interface này

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository // Đánh dấu đây là một Repository (DAO - Data Access Object)
public interface JpaDiscount extends JpaRepository<EntityDiscount, Integer> {

    // Tìm kiếm khuyến mãi theo salePrice
    List<EntityDiscount> findBySalePrice(Float salePrice);
  
    
    // Nếu cần các phương thức truy vấn tùy chỉnh, có thể khai báo thêm tại đây
}
