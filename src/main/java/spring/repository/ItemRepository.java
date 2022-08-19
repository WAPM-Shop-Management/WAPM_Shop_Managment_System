package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
