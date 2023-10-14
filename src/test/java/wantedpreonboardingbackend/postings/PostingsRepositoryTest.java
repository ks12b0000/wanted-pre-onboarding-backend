package wantedpreonboardingbackend.postings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wantedpreonboardingbackend.company.CompanyRepository;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;
import wantedpreonboardingbackend.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PostingsRepositoryTest {

    @Autowired
    private PostingsRepository postingsRepository;

    // 채용 공고 등록을 위한 유저, 회사 데이터 추가.
    private static final User user = new User("example123", "example123", "example12@gmail.com", "company");
    private static final Company company = new Company("원티드", "한국", "판교", user);

    @Test
    @DisplayName("채용 공고 저장 테스트")
    void postingsSave() {
        // given
        Postings postings = new Postings(company, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python", user);

        // when
        Postings savePostings = postingsRepository.save(postings);

        // then
        assertAll(
                () -> assertThat(savePostings.getCompany()).isEqualTo(company),
                () -> assertThat(savePostings.getId()).isEqualTo(postings.getId()),
                () -> assertThat(savePostings.getPosition()).isEqualTo(postings.getPosition()),
                () -> assertThat(savePostings.getCompensation()).isEqualTo(postings.getCompensation()),
                () -> assertThat(savePostings.getContents()).isEqualTo(postings.getContents()),
                () -> assertThat(savePostings.getTechnology()).isEqualTo(postings.getTechnology())
        );
    }

    @Test
    @DisplayName("채용 공고 삭제 테스트")
    void postingsDelete() {
        // given
        Postings postings = new Postings(company, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python", user);

        // when
        Postings savePostings = postingsRepository.save(postings);
        postingsRepository.delete(postings);

        // then
        assertThat(postingsRepository.findById(savePostings.getId())).isEmpty();
    }

    @Test
    @DisplayName("채용 공고 목록 조회 테스트")
    void postingsList() {
        // given
        // when
        List<Postings> list = postingsRepository.findAll();

        // then
        assertThat(list).isNotNull();
    }

    @Test
    @DisplayName("채용 공고 검색 테스트")
    void postingsSearchList() {
        // given
        // when
        List<Postings> list = postingsRepository.findByTechnologyContaining("Python");

        // then
        assertThat(list).isNotNull();
    }

    @Test
    @DisplayName("채용 공고 상세 조회 테스트")
    void postingsDetail() {
        // given
        Postings postings = new Postings(company, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python", user);

        // when
        Postings savePostings = postingsRepository.save(postings);
        Optional<Postings> list = postingsRepository.findById(savePostings.getId());

        // then
        assertThat(list).isNotNull();
    }

}
