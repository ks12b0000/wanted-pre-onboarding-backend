package wantedpreonboardingbackend.apply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.apply.dto.ApplyRequest;
import wantedpreonboardingbackend.domain.ApplyHistory;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;
import wantedpreonboardingbackend.postings.PostingsRepository;
import wantedpreonboardingbackend.response.BaseException;
import wantedpreonboardingbackend.user.UserRepository;

import static wantedpreonboardingbackend.response.BaseExceptionStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository applyRepository;

    private final UserRepository userRepository;

    private final PostingsRepository postingsRepository;

    /**
     * 채용 공고 지원
     * @param request
     */
    @Override
    @Transactional
    public void postingsApply(ApplyRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        Postings postings = postingsRepository.findById(request.getPostingsId()).orElseThrow(() -> new BaseException(NOT_EXIST_POSTINGS));

        // 채용 공고 지원 목록에서 채용 공고 아이디, 유저 아이디로 조회
        ApplyHistory history = applyRepository.findByPostingsIdAndUserId(postings.getId(), user.getId());
        // 이미 지원한 채용 공고라면 예외처리.
        if (history != null) throw new BaseException(DUPLICATION_APPLY);

        ApplyHistory applyHistory = new ApplyHistory(postings, user);

        applyRepository.save(applyHistory);
    }
}
