package wantedpreonboardingbackend.postings;

import wantedpreonboardingbackend.postings.dto.*;

import java.util.List;

public interface PostingsService {

    void postingsSave(Long userId, PostingsWriteRequest request);

    void postingsUpdate(Long postingsId, Long userId, PostingsUpdateRequest request);

    void postingsDelete(Long postingsId, Long userId);

    List<PostingsListResponse> postingsList();

    List<PostingsListResponse> postingsSearchList(String keyword);

    PostingsDetailResponse postingsDetail(Long postingsId);

}
