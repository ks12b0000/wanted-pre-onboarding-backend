package wantedpreonboardingbackend.postings;

import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;

public interface PostingsService {

    void postingsSave(PostingsWriteRequest request);
}
