package wantedpreonboardingbackend.postings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.company.CompanyRepository;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.domain.User;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;
import wantedpreonboardingbackend.response.BaseException;
import wantedpreonboardingbackend.user.UserRepository;

import static wantedpreonboardingbackend.response.BaseExceptionStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingsServiceImpl implements PostingsService{

    private final PostingsRepository postingsRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    /**
     * 채용 공고 등록
     * @param request
     */
    @Override
    @Transactional
    public void postingsSave(Long userId, PostingsWriteRequest request) {
        // 유저가 없는 경우 예외처리
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        // 유저의 권한이 company가(관리자가 채용 공고 등록 가능한 유저 권한 부여) 아닌 경우 예외처리
        if (!user.getRole().equals("company")) throw new BaseException(WITHOUT_ACCESS_USER);

        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new BaseException(NOT_EXIST_COMPANY));

        // 공고를 등록 하려는 유저의 회사와 일치하지 않는 회사를 선택해서 공고를 올릴 경우 예외처리
        if (user.getId() != company.getUser().getId()) throw new BaseException(CHECK_THE_COMPANY);

        Postings postings = new Postings(company, request.getPosition(), request.getCompensation(), request.getContents(), request.getTechnology(), company.getUser());

        postingsRepository.save(postings);
    }
}
