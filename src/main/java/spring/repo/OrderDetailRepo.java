package spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.entity.OrderDetail;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, String> {

    OrderDetail findById(Integer id);

}
