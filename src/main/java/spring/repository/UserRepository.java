package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.dto.row.DashboardRowData;
import spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByTel(String tel);

    @Query(value = "SELECT(SELECT COUNT(id) FROM orders WHERE order_status='PENDING') AS pending,\n" +
            "       (SELECT COUNT(id) FROM orders WHERE order_status='PROCESSING') AS processing,\n" +
            "       (SELECT COUNT(id) FROM orders WHERE order_status='COMPLETED') AS completed,\n" +
            "       (SELECT COUNT(id) FROM user) AS customers", nativeQuery = true)
    DashboardRowData getDashboardDetails();
}
