package wantedpreonboardingbackend.apply;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wantedpreonboardingbackend.apply.dto.ApplyRequest;
import wantedpreonboardingbackend.response.BaseResponse;
import wantedpreonboardingbackend.response.ValidationSequence;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Apply", description = "채용 공고 지원 관련 API")
public class ApplyController {

    private final ApplyService applyService;

    @Operation(summary = "채용 공고 지원 API", responses = {
            @ApiResponse(responseCode = "200", description = "채용 공고 지원에 성공했습니다.")
    })
    @Tag(name = "Apply")
    @PostMapping("/api/postings/apply")
    public BaseResponse postingsApply(@Validated(ValidationSequence.class) @RequestBody ApplyRequest request) {
        applyService.postingsApply(request);

        return new BaseResponse("채용 공고 지원에 성공했습니다.");
    }
}
