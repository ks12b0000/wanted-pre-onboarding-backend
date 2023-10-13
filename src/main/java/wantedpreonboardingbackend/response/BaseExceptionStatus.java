package wantedpreonboardingbackend.response;

import lombok.Getter;

@Getter
public enum BaseExceptionStatus {

    SERVER_INTERNAL_ERROR(2002, "서버 내부적인 에러"),
    NOT_EXIST_COMPANY(3001, "존재하지 않는 회사입니다."),
    NOT_EXIST_USER(4001, "존재하지 않는 유저입니다."),
    WITHOUT_ACCESS_USER(4002, "접근 권한이 없는 유저입니다."),
    CHECK_THE_COMPANY(4003, "등록 하려는 회사를 확인 해주세요."),
    NOT_EXIST_POSTINGS(4004, "존재하지 않는 채용 공고입니다.");
    private final int code;
    private final String message;

    private BaseExceptionStatus(int code, String msg){
        this.code = code;
        this.message = msg;
    }
}
