package spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByNic(String nic);
}
