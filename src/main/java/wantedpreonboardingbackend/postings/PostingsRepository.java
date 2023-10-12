package wantedpreonboardingbackend.postings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wantedpreonboardingbackend.domain.Postings;

@Repository
public interface PostingsRepository extends JpaRepository<Postings, Long> {


}
