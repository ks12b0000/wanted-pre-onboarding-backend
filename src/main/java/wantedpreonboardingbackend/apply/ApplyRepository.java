package wantedpreonboardingbackend.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import wantedpreonboardingbackend.domain.ApplyHistory;

public interface ApplyRepository extends JpaRepository<ApplyHistory, Long> {

    ApplyHistory findByPostingsIdAndUserId(Long postingsId, Long userId);
}
