package spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.Item;
import spring.entity.User;

public interface ItemRepo extends JpaRepository<Item, Integer> {

}
