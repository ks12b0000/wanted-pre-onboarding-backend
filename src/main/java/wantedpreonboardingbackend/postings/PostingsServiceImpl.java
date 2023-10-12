package wantedpreonboardingbackend.postings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wantedpreonboardingbackend.company.CompanyRepository;
import wantedpreonboardingbackend.domain.Company;
import wantedpreonboardingbackend.domain.Postings;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;
import wantedpreonboardingbackend.response.BaseException;

import static wantedpreonboardingbackend.response.BaseExceptionStatus.NOT_EXIST_COMPANY;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingsServiceImpl implements PostingsService{

    private final PostingsRepository postingsRepository;

    private final CompanyRepository companyRepository;

    /**
     * 채용 공고 등록
     * @param request
     */
    @Override
    @Transactional
    public void postingsSave(PostingsWriteRequest request) {
        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new BaseException(NOT_EXIST_COMPANY));

        Postings postings = new Postings(company, request.getPosition(), request.getCompensation(), request.getContents(), request.getTechnology());

        postingsRepository.save(postings);
    }
}
