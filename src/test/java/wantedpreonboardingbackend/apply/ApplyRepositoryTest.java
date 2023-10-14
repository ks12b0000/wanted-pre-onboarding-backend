package wantedpreonboardingbackend.apply;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wantedpreonboardingbackend.domain.ApplyHistory;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ApplyRepositoryTest {

    @Autowired
    private ApplyRepository applyRepository;

    private static final User user = new User("example123", "example123", "example12@gmail.com", "company");
    private static final Company company = new Company("원티드", "한국", "판교", user);
    private static final Postings postings = new Postings(company, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python", user);

    @Test
    @DisplayName("채용 공고 지원 저장 테스트")
    void postingsApply() {
        // given
        ApplyHistory applyHistory = new ApplyHistory(postings, user);

        // when
        ApplyHistory saveHistory = applyRepository.save(applyHistory);

        // then
        assertAll(
                () -> assertThat(saveHistory.getPostings()).isEqualTo(postings),
                () -> assertThat(saveHistory.getUser()).isEqualTo(user)
        );
    }

}
