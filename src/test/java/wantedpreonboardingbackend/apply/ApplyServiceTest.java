package wantedpreonboardingbackend.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.apply.dto.ApplyRequest;
import wantedpreonboardingbackend.domain.ApplyHistory;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;
import wantedpreonboardingbackend.postings.PostingsRepository;
import wantedpreonboardingbackend.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@Slf4j
@Transactional
@ExtendWith(MockitoExtension.class)
public class ApplyServiceTest {

    @InjectMocks
    private ApplyServiceImpl applyService;

    @Mock
    private ApplyRepository applyRepository;

    @Mock
    private PostingsRepository postingsRepository;

    @Mock
    private UserRepository userRepository;

    private static final User user = new User("example123", "example123", "example12@gmail.com", "company");
    private static final Company company = new Company("원티드", "한국", "판교", user);
    private static final Postings postings = new Postings(company, "백엔드 주니어 개발자", 1000000L, "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", "Python", user);

    @Test
    @DisplayName("채용 공고 지원 테스트")
    void postingsSave() {
        // given
        ApplyRequest request = new ApplyRequest(postings.getId(), user.getId());
        ApplyHistory applyHistory = new ApplyHistory(postings, user);

        // stub
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(postingsRepository.findById(request.getPostingsId())).thenReturn(Optional.of(postings));
        when(applyRepository.findByPostingsIdAndUserId(request.getPostingsId(), request.getUserId())).thenReturn(null);

        // when
        applyService.postingsApply(request);

        // then
        assertAll(
                () -> assertThat(applyHistory.getUser()).isEqualTo(user),
                () -> assertThat(applyHistory.getPostings()).isEqualTo(postings)
        );
    }
}
