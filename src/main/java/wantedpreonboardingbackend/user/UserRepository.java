package wantedpreonboardingbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wantedpreonboardingbackend.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
