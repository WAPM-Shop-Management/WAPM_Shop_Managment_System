package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.entity.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT *\n" +
            "FROM orders o\n" +
            "WHERE (?1 = 0 OR o.user_id = ?1)\n" +
            "  AND (?2 IS NULL OR o.order_status = ?2)", nativeQuery = true)
    List<Orders> filterOrders(int userId, String status);
}
