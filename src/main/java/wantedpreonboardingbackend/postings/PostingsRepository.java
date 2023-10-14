package wantedpreonboardingbackend.postings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;

import java.util.List;

@Repository
public interface PostingsRepository extends JpaRepository<Postings, Long> {

    List<Postings> findByTechnologyContaining(@Param("keyword") String keyword);

    @Query("select a.id from Postings a where a.company = :company")
    List<Long> findCompanyOtherPostingsId(@Param("company") Company company);
}
