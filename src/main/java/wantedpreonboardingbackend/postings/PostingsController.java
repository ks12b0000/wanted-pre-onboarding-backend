package wantedpreonboardingbackend.postings;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wantedpreonboardingbackend.postings.dto.PostingsUpdateRequest;
import wantedpreonboardingbackend.postings.dto.PostingsWriteRequest;
import wantedpreonboardingbackend.response.BaseResponse;
import wantedpreonboardingbackend.response.ValidationSequence;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Postings", description = "채용 공고 API")
public class PostingsController {

    private final PostingsService postingsService;

    @Operation(summary = "채용 공고 등록 API", responses = {
            @ApiResponse(responseCode = "200", description = "채용 공고 등록에 성공했습니다.")
    })
    @Tag(name = "Postings")
    @PostMapping("/api/postings/save/{userId}")
    public BaseResponse postingsSave(@PathVariable Long userId, @Validated(ValidationSequence.class) @RequestBody PostingsWriteRequest request) {

        postingsService.postingsSave(userId, request);

        return new BaseResponse("채용 공고 등록에 성공했습니다.");
    }

    @Operation(summary = "채용 공고 수정 API", responses = {
            @ApiResponse(responseCode = "200", description = "채용 공고 수정에 성공했습니다.")
    })
    @Tag(name = "Postings")
    @PatchMapping("/api/postings/{postingsId}/{userId}")
    public BaseResponse postingsUpdate(@PathVariable Long postingsId, @PathVariable Long userId,
                                       @Validated(ValidationSequence.class) @RequestBody PostingsUpdateRequest request) {
        postingsService.postingsUpdate(postingsId, userId, request);

        return new BaseResponse("채용 공고 수정에 성공했습니다.");
    }
}
