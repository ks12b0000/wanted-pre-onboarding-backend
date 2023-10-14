package wantedpreonboardingbackend.apply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wantedpreonboardingbackend.response.ValidationGroup;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRequest {

    @Schema(description = "채용 공고 아이디", example = "1", required = true)
    @NotNull(message = "채용 공고 아이디를 입력해주세요.", groups = ValidationGroup.NotNullGroup.class)
    private Long postingsId;

    @Schema(description = "유저 아이디", example = "1", required = true)
    @NotNull(message = "유저 아이디를 입력해주세요.", groups = ValidationGroup.NotNullGroup.class)
    private Long userId;
}
