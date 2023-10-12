package wantedpreonboardingbackend.postings.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wantedpreonboardingbackend.response.ValidationGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostingsWriteRequest {

    @Schema(description = "회사 아이디", example = "1", required = true)
    @NotNull(message = "회사 아이디를 입력해주세요.", groups = ValidationGroup.NotNullGroup.class)
    private Long companyId;

    @Schema(description = "채용 포지션", example = "백엔드 주니어 개발자", required = true)
    @NotBlank(message = "채용 포지션을 입력해주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String position;

    @Schema(description = "채용 보상금", example = "1000000", required = true)
    @NotNull(message = "채용 보상금을 입력해주세요.", groups = ValidationGroup.NotNullGroup.class)
    private Long compensation;


    @Schema(description = "채용 내용", example = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..", required = true)
    @NotBlank(message = "채용 내용을 입력해주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String contents;

    @Schema(description = "사용 기술", example = "Python", required = true)
    @NotBlank(message = "사용 기술을 입력해주세요.", groups = ValidationGroup.NotBlankGroup.class)
    private String technology;

}
