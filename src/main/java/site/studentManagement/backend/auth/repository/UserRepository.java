package site.studentManagement.backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.studentManagement.backend.auth.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String > {
    Optional<User> findByEmail(String email);
}
