package spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Item;
import spring.entity.User;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {

}
