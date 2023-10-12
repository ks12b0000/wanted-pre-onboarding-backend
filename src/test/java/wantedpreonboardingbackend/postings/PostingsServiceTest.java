package wantedpreonboardingbackend.postings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.company.CompanyRepository;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;
import wantedpreonboardingbackend.user.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
public class PostingsServiceTest {

    @InjectMocks
    private PostingsServiceImpl postingsService;

    @Mock
    private PostingsRepository postingsRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    // 채용 공고 등록을 위한 유저, 회사 데이터 추가.
    private static final User user = new User("example123", "example123", "example12@gmail.com", "company");
    private static final Company company = new Company("원티드", "한국", "판교", user);

    @Test
    @DisplayName("채용 공고 등록 테스트")
    void postingsSave() {
        // given
        PostingsWriteRequest request = new PostingsWriteRequest(1L, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python");
        Postings postings = new Postings(company, request.getPosition(), request.getCompensation(), request.getContents(), request.getTechnology(), user);

        // stub
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(companyRepository.findById(request.getCompanyId())).thenReturn(Optional.of(company));
        when(postingsRepository.save(any())).thenReturn(postings);

        // when
        postingsService.postingsSave(user.getId(), request);

        // then
    }
}
