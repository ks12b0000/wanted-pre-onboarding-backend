package wantedpreonboardingbackend.postings;

import wantedpreonboardingbackend.postings.dto.PostingsUpdateRequest;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;

public interface PostingsService {

    void postingsSave(Long userId, PostingsWriteRequest request);

    void postingsUpdate(Long postingsId, Long userId, PostingsUpdateRequest request);

    void postingsDelete(Long postingsId, Long userId);
}
