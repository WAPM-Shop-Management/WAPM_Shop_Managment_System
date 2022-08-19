package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByTel(String tel);
}
