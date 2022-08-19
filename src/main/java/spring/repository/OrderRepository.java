package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
