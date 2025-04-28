// package t2406e_group1.bookshopspringboot.user;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

// public interface JpaUser extends JpaRepository<EntityUser, Integer>
// // Cùng cấp thì ko cần import file
// {
// // Bạn có thể định nghĩa các phương thức truy vấn tùy chỉnh tại đây
// Optional<EntityUser> findByEmail(String email); // Thêm phương thức tìm kiếm theo email
// List<EntityUser> findByRolesContaining(String roles);  // Tìm User theo vai trò
// }
package t2406e_group1.bookshopspringboot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaUser extends JpaRepository<EntityUser, Integer> {
    Optional<EntityUser> findByEmail(String email);

    @Query("SELECT u FROM EntityUser u JOIN u.roles r WHERE r LIKE %:role%")
    List<EntityUser> findByRolesContaining(@Param("role") String role);
}