package wantedpreonboardingbackend.postings;

import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;

public interface PostingsService {

    void postingsSave(Long userId, PostingsWriteRequest request);
}
