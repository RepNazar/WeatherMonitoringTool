package ua.Nazar.Rep.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.Nazar.Rep.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    /**/    User findByActivationCode(String code);
}
